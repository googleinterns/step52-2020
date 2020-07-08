package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

/**
* Store information needed to keep track of a list of a positive user's contacts from People and Calendar API
*/
@Entity
public class PositiveUserContacts {

  @Id String userId;
  ArrayList<PotentialContact> contacts;
  long timeCreatedSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUserContacts() {}

  public PositiveUserContacts(String userId, ArrayList<PotentialContact> contacts) {
    this.userId = userId;
    this.contacts = contacts;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

  //TODO
  public void mergeContactLists(ArrayList<PotentialContact> contactsFromPeople, ArrayList<PotentialContact> contactsFromCalendar) {}

}
