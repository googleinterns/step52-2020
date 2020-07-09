package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* This class keeps track of a positive user's list of locations from location history
*/
@Entity
public class PositiveUserLocations {

  @Id private String userId;
  @Index private long timeCreatedSeconds;
  private ArrayList<Location> listOfLocations;

  // Objecify requires one constructor with no parameters
  private PositiveUserLocations() {}

  public PositiveUserLocations(String id, ArrayList<Location> locations) {
    userId = id;
    timeCreatedSeconds = Instant.now().getEpochSecond();
    listOfLocations = locations;
  }

  public String getUserId() {
    return userId;
  }

  public ArrayList<Location> getListOfLocations() {
    return listOfLocations;
  }

  public long getTimeCreatedSeconds() {
    return timeCreatedSeconds;
  }

}