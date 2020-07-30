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
public class PositiveUserPlaces {

  @Id private String userId;
  @Index private long timeCreatedSeconds;
  private ArrayList<Place> listOfPlaces;

  // Objecify requires one constructor with no parameters
  private PositiveUserPlaces() {}

  public PositiveUserPlaces(String id) {
    userId = id;
    timeCreatedSeconds = Instant.now().getEpochSecond();
    listOfPlaces = new ArrayList<Place>();
  }

  public String getUserId() {
    return userId;
  }

  public ArrayList<Place> getListOfPlaces() {
    return listOfPlaces;
  }

  public long getTimeCreatedSeconds() {
    return timeCreatedSeconds;
  }

  public void add(String placeId, String name, long intervalStart, long intervalEnd) {
    listOfPlaces.add(new Place(placeId, name, intervalStart, intervalEnd));
  }

  @Override
  public String toString() {
    return String.format("The user (%s) has been to %s", userId, listOfPlaces.toString());
  }
}