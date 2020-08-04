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
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.googlecode.objectify.Objectify;

import com.onlinecontacttracing.storage.Constants;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.PotentialContact;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.Set;

class PeopleDataForPositiveUser implements Runnable {
  private static final String APPLICATION_NAME = "Get People Data From Positive User";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  private final Objectify ofy;
  private final String userId;
  private final Credential credential;
  static final Logger log = Logger.getLogger(PeopleDataForPositiveUser.class.getName());

  private static final List<String> SCOPES = Arrays.asList(PeopleServiceScopes.CONTACTS_READONLY);

  public PeopleDataForPositiveUser(Objectify ofy, String userId, Credential credential) {
    this.ofy = ofy;
    this.userId = userId;
    this.credential = credential;
  }

  @Override
  public void run() {
    // Initiate objects to store information
    PositiveUserContacts positiveUserContacts = new PositiveUserContacts(userId);

    try {
      final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // Get user's calendar
      PeopleService service = new PeopleService.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();

      // Request All connections.
      ListConnectionsResponse response = service.people().connections()
        .list("people/me")
        .setPersonFields("names,emailAddresses")
        .execute();

      // Store all connections available.
      List<Person> connections = response.getConnections();
      if (connections != null && connections.size() > 0) {
        for (Person person : connections) {
            List<Name> names = person.getNames();
            if (names != null && names.size() > 0) {
                positiveUserContacts.add(person.getNames().get(0).getDisplayName(),
                 person.getEmailAddresses().get(0).getDisplayName());
            }
        }
      // Store data or replace old data with newer data.
      if(!positiveUserContacts.getListOfContacts().isEmpty()) {
        ofy.save().entity(positiveUserContacts).now();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      log.warning("An exception occurred: " + e.toString());
    }
  }
}