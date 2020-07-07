package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class PositiveUserContacts {

  @Id String userId;
  ArrayList<PotentialContact> contacts;
  long timeCreatedUnixTimeSeconds;

  private PositiveUserContacts() {}

  public PositiveUserContacts(String userId, ArrayList<PotentialContact> contacts) {
    this.userId = userId;
    this.contacts = contacts;
    timeCreatedUnixTimeSeconds = Instant.now().getEpochSecond();
  }

  //TODO
  public void mergeContactLists(ArrayList<PotentialContact> contactsFromPeople, ArrayList<PotentialContact> contactsFromCalendar) {}

}
