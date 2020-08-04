package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
* This class keeps track of a positive user's list of contacts from People and Calendar API
*/
@Entity
public class PositiveUserContacts {

  @Id private String userId;
  @Index private long timeCreatedSeconds;
  private Set<PotentialContact> listOfContacts;

  // Objecify requires one constructor with no parameters
  private PositiveUserContacts() {}

  public PositiveUserContacts(String id) {
    userId = id;
    timeCreatedSeconds = Instant.now().getEpochSecond();
    listOfContacts = new HashSet<PotentialContact>();
  }

  public String getUserId() {
    return userId;
  }

  public Set<PotentialContact> getListOfContacts() {
    return listOfContacts;
  }

  public long getTimeCreatedSeconds() {
    return timeCreatedSeconds;
  }

  /*
  *  This method will take in the name and email of someone to contact
  *  and create a PotentialContact to add to the set.
  */
  public void add(String name, String email) {
    listOfContacts.add(new PotentialContact(name, email));
  }

  //TODO
  public void mergeContactListsFromPeopleAPI(Set<PotentialContact> contactsFromPeople) {
      for(PotentialContact contact: contactsFromPeople) {
          listOfContacts.add(new PotentialContact(contact.getName(), contact.getEmail()));
      }
  }

  @Override
  public String toString() {
    return listOfContacts.toString();
  }
}