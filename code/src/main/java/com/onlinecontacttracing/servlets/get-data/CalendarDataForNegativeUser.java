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
import com.onlinecontacttracing.storage.NegativeUserPlace;
import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResult;
import com.googlecode.objectify.Objectify;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;

class CalendarDataForNegativeUser implements Runnable {

  private static final String APPLICATION_NAME = "Get Calendar Data From Negative User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String calendarType = "primary";
  static final Logger log = Logger.getLogger(CalendarDataForNegativeUser.class.getName());
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/apiKey.txt";

  private final Objectify ofy;
  private final String userId;
  private final Credential credential;

  public CalendarDataForNegativeUser(Objectify ofy, String userId, Credential credential) {
    this.ofy = ofy;
    this.userId = userId;
    this.credential = credential;
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
      Events events = service.events().list("primary")
        .setTimeMin(startOfContactsQueryWindow)
        .setTimeMax(now)
        .execute();

      List<NegativeUserPlace> negativeUserPlaces = new ArrayList<NegativeUserPlace>();
      // Iterate through events to extract places
      for (Event event : events.getItems()) {
        Optional<String> addressOptional = Optional.ofNullable(event.getLocation());
        
        if (addressOptional.isPresent()) {
          String address = addressOptional.get();
          PlacesSearchResult[] results = PlacesApi.textSearchQuery(context, address).await().results;
          if (results.length != 0) {
            String placeId = results[0].placeId;
            long startTimeSeconds = event.getStart().getDateTime().getValue() / 1000;
            long endTimeSeconds = event.getEnd().getDateTime().getValue() / 1000;

            negativeUserPlaces.add(new NegativeUserPlace(userId, placeId, address, startTimeSeconds, endTimeSeconds));
          }
        }
      }

      // Store data or replace old data with newer data.
      ofy.save().entities(negativeUserPlaces).now();

    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }
}