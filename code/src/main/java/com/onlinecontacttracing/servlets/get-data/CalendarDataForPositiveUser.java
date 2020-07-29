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
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.google.api.services.calendar.model.EventAttendee;
import java.util.List;
import java.util.Collections;
import java.util.Optional;
import com.googlecode.objectify.Objectify;
import java.util.logging.Logger;

class CalendarDataForPositiveUser implements Runnable {

  private static final String APPLICATION_NAME = "Get Calendar Data From Positive User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String calendarType = "primary";
  static final Logger log = Logger.getLogger(CalendarDataForPositiveUser.class.getName());

  private final Objectify ofy;
  private final String userId;
  private final Credential credential;

  public CalendarDataForPositiveUser(Objectify ofy, String userId, Credential credential) {
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

      // Query events between now and the API_QUERY_TIME
      long currentTime = System.currentTimeMillis();
      DateTime now = new DateTime(currentTime);
      DateTime startOfContactsQueryWindow = new DateTime(currentTime - Constants.API_QUERY_TIME);
      Events events = service.events().list(calendarType)
        .setTimeMin(startOfContactsQueryWindow)
        .setTimeMax(now)
        .execute();

      // Initiate objects to store information
      PositiveUserContacts positiveUserContacts = new PositiveUserContacts(userId);
      
      // Iterate through events to extract contacts and places
      for (Event event : events.getItems()) {
        List<EventAttendee> attendees = Optional.ofNullable(event.getAttendees()).orElse(Collections.emptyList());

        for (EventAttendee attendee : attendees) {
          positiveUserContacts.add(attendee.getDisplayName(), attendee.getEmail());
        }
      }

      // Store data or replace old data with newer data.
      ofy.save().entity(positiveUserContacts).now();

      // TODO: add positiveUserLocations
      
    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }
}