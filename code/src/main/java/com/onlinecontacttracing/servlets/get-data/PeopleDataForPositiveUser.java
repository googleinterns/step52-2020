package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.googlecode.objectify.Objectify;

class PeopleDataForPositiveUser implements Runnable {
  private final Objectify ofy;
  private final String userId;
  private final Credential credential;

  public PeopleDataForPositiveUser(Objectify ofy, String userId, Credential credential) {
    this.ofy = ofy;
    this.userId = userId;
    this.credential = credential;
  }

  @Override
  public void run() {
    // Initiate objects to store information
    PositiveUserContacts positiveUserContacts = new PositiveUserContacts(userId);

    // TODO: add people api

    // Store data or replace old data with newer data.
    // ofy.save().entity(positiveUserContacts).now();
  }
}