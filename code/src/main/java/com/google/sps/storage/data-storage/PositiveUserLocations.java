package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

/**
* Store information needed to keep track of a list of a positive user's locations from location history
*/
@Entity
public class PositiveUserLocations {

  @Id String userId;
  ArrayList<Location> locations;
  long timeCreatedSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUserLocations() {}

  public PositiveUserLocations(String userId, ArrayList<Location> locations) {
    this.userId = userId;
    this.locations = locations;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

}
