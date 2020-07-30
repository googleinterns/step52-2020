package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.onlinecontacttracing.storage.Constants;
import com.google.api.services.calendar.model.EventAttendee;
import java.util.List;
import java.util.Collections;
import java.util.Optional;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResult;
import com.googlecode.objectify.Objectify;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.logging.Logger;
import java.util.Set;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.google.maps.errors.ApiException;
import java.lang.InterruptedException;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

class CalendarDataForPositiveUser implements Runnable {

  private static final String APPLICATION_NAME = "Get Calendar Data From Positive User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String calendarType = "primary";
  static final Logger log = Logger.getLogger(CalendarDataForPositiveUser.class.getName());
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/apiKey.txt";

  private final Objectify ofy;
  private final String userId;
  private final Credential credential;
  Set<PotentialContact> contacts;
  private final PositiveUserPlaces positiveUserPlaces;

  public CalendarDataForPositiveUser(Objectify ofy, String userId, Credential credential, Set<PotentialContact> contacts) {
    this.ofy = ofy;
    this.userId = userId;
    this.credential = credential;
    this.contacts = contacts;
    this.positiveUserPlaces = new PositiveUserPlaces(userId);
  }

  @Override
  public void run() {
    try {
      final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // Get user's calendar
      Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
        
      InputStream apiKeyStream = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
      String apiKey = IOUtils.toString(apiKeyStream, StandardCharsets.UTF_8);
      apiKeyStream.close();

      // Set up Places API
      GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();

      // Query events between now and the SPAN_OF_TIME_TO_COLLECT_DATA
      long currentTime = System.currentTimeMillis();
      DateTime now = new DateTime(currentTime);
      DateTime startOfContactsQueryWindow = new DateTime(currentTime - Constants.SPAN_OF_TIME_TO_COLLECT_DATA);
      Events events = service.events().list(calendarType)
        .setTimeMin(startOfContactsQueryWindow)
        .setTimeMax(now)
        .execute();
      
      // Iterate through events to extract contacts and places
      for (Event event : events.getItems()) {
        contacts.addAll(getContactsFromEvent(event));
        getPlacesFromEvent(event, context);
      }

      // Store data or replace old data with newer data.
      ofy.save().entity(positiveUserPlaces).now();
      
    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }

  private List<PotentialContact> getContactsFromEvent(Event event) {
    List<EventAttendee> attendees = Optional.ofNullable(event.getAttendees()).orElse(Collections.emptyList());
    return attendees.stream()
      .map(attendee -> new PotentialContact(attendee.getDisplayName(), attendee.getEmail()))
      .collect(Collectors.toList());
  }

  private void getPlacesFromEvent(Event event, GeoApiContext context) throws ApiException, InterruptedException, IOException {
    Optional<String> addressOptional = Optional.ofNullable(event.getLocation());
    
    if (addressOptional.isPresent()) {
      String address = addressOptional.get();
      PlacesSearchResult[] results = PlacesApi.textSearchQuery(context, address).await().results;
      if (results.length != 0) {
        String placeId = results[0].placeId;
        long startTimeSeconds = event.getStart().getDateTime().getValue() / 1000;
        long endTimeSeconds = event.getEnd().getDateTime().getValue() / 1000;

        positiveUserPlaces.add(placeId, address, startTimeSeconds, endTimeSeconds);
      }
    }
  }
}