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
import com.googlecode.objectify.Objectify;
import java.util.logging.Logger;
import java.util.Set;
import com.onlinecontacttracing.storage.PotentialContact;

class CalendarDataForPositiveUser implements Runnable {

  private static final String APPLICATION_NAME = "Get Calendar Data From Positive User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String calendarType = "primary";
  static final Logger log = Logger.getLogger(CalendarDataForPositiveUser.class.getName());

  private final Objectify ofy;
  private final String userId;
  private final Credential credential;
  Set<PotentialContact> contacts;

  public CalendarDataForPositiveUser(Objectify ofy, String userId, Credential credential, Set<PotentialContact> contacts) {
    this.ofy = ofy;
    this.userId = userId;
    this.credential = credential;
    this.contacts = contacts;
  }

  @Override
  public void run() {
    try {
      final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // Get user's calendar
      Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
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
        List<EventAttendee> attendees = Optional.ofNullable(event.getAttendees()).orElse(Collections.emptyList());

        for (EventAttendee attendee : attendees) {
          contacts.add(new PotentialContact(attendee.getDisplayName(), attendee.getEmail()));
        }
      }

      // TODO: add positiveUserLocations
      
    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }
}