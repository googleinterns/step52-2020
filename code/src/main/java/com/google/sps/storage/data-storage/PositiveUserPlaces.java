package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* Store information needed to keep track of a list of a positive user's contacts from People and Calendar API
*/
@Entity
public class PositiveUserPlaces {

  @Id private String userId;
  private ArrayList<Place> places;
  @Index private long timeCreatedSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUserPlaces() {}

  public PositiveUserPlaces(String userId, ArrayList<Place> places) {
    this.userId = userId;
    this.places = places;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

  public String getUserId() {
      return userId;
  }

  public ArrayList<Place> getPlaces() {
      return places;
  }

  public long getTimeCreatedSeconds() {
      return timeCreatedSeconds;
  }

}
