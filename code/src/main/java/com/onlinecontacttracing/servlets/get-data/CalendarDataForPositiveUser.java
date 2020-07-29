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

class CalendarDataForPositiveUser implements Runnable {

  private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private final String userId;
  private final Credential credential;
  private final Objectify ofy;

  public CalendarDataForPositiveUser(Objectify ofy, String userId, Credential credential) {
    this.userId = userId;
    this.credential = credential;
    this.ofy = ofy;
  }

  @Override
  public void run() {
    try {
      final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      // Get user's calendar
      Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();

      // Query events between now and the last two weeks
      long currentTime = System.currentTimeMillis();
      DateTime now = new DateTime(currentTime);
      DateTime twoWeeksAgo = new DateTime(currentTime-Constants.CONTACTS_QUERY_TIME);
      Events events = service.events().list("primary")
        .setTimeMin(twoWeeksAgo)
        .setTimeMax(now)
        .execute();
      List<Event> items = events.getItems();

      // Initiate objects to store information
      PositiveUserContacts positiveUserContacts = new PositiveUserContacts(userId);
      
      // Iterate through events to extract contacts and places
      for (Event event : items) {
        Optional<List<EventAttendee>> attendees = Optional.ofNullable(event.getAttendees());

        for (EventAttendee attendee : attendees.orElse(Collections.emptyList())) {
          positiveUserContacts.add(attendee.getDisplayName(), attendee.getEmail());
        }
      }

      ofy.save().entity(positiveUserContacts).now();
      
    } catch (Exception e) {
      e.printStackTrace();
      // Not sure what to do here, I tried throwing the exception but then run() isn't overwritten
    }
  }
}