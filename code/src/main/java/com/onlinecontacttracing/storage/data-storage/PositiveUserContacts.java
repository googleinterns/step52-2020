package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
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

  public List<PotentialContact> getListOfContacts() {
    if (listOfContacts != null) {
      List<PotentialContact> sortedContacts = new ArrayList(listOfContacts);
      // Sort contacts by name. Null names are sorted by email.
      Collections.sort(sortedContacts);
      return sortedContacts;
    } else {
      return Collections.emptyList();
    }
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
  public void mergeContactListsFromCalendarAPI(Set<PotentialContact> contactsFromCalendar) {
    listOfContacts.addAll(contactsFromCalendar);
  }

  @Override
  public String toString() {
    return listOfContacts.toString();
  }
}