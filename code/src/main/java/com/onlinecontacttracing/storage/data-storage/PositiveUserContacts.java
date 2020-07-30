package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* This class keeps track of a positive user's list of contacts from People and Calendar API
*/
@Entity
public class PositiveUserContacts {

  @Id private String userId;
  @Index private long timeCreatedSeconds;
  private ArrayList<PotentialContact> listOfContacts;

  // Objecify requires one constructor with no parameters
  private PositiveUserContacts() {}

  public PositiveUserContacts(String id, ArrayList<PotentialContact> contactsFromCalendar) {
    userId = id;
    timeCreatedSeconds = Instant.now().getEpochSecond();
    listOfContacts = contactsFromCalendar;
  }

  public String getUserId() {
    return userId;
  }

  public ArrayList<PotentialContact> getListOfContacts() {
    return listOfContacts;
  }

  public long getTimeCreatedSeconds() {
    return timeCreatedSeconds;
  }

  //TODO
  public void mergeContactListsFromPeopleAPI(ArrayList<PotentialContact> contactsFromPeople) {}

}