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

class CalendarDataForNegativeUser implements Runnable {

  private static final String APPLICATION_NAME = "Get Calendar Data From Negative User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String calendarType = "primary";
  static final Logger log = Logger.getLogger(CalendarDataForNegativeUser.class.getName());

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

      // Set up Places API
      GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyBMrfBNGcVEtoRsoduXvYSjd9piD36W7Qg")
        .build();

      // Query events between now and the last two weeks
      long currentTime = System.currentTimeMillis();
      DateTime now = new DateTime(currentTime);
      DateTime twoWeeksAgo = new DateTime(currentTime - Constants.API_QUERY_TIME);
      Events events = service.events().list("primary")
        .setTimeMin(twoWeeksAgo)
        .setTimeMax(now)
        .execute();

      // Iterate through events to extract places
      for (Event event : events.getItems()) {
        Optional<String> addressOptional = Optional.ofNullable(event.getLocation());
        
        if (addressOptional.isPresent()) {
          String address = addressOptional.get();
          PlacesSearchResult[] results = PlacesApi.textSearchQuery(context, address).await().results;
          if (results.length != 0) {
            String placeId = results[0].placeId;
            long startTimeSeconds = event.getStart().getDateTime().getValue()/1000;
            long endTimeSeconds = event.getEnd().getDateTime().getValue()/1000;

            // Save/replace negative user place from event
            ofy.save().entity(new NegativeUserPlace(userId, placeId, address, startTimeSeconds, endTimeSeconds)).now();
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }
}