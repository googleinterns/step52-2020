package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class PositiveUserLocations {

  @Id String userId;
  ArrayList<Location> locations;
  long timeCreatedSeconds;

  private PositiveUserLocations() {}

  public PositiveUserLocations(String userId, ArrayList<Location> locations) {
    this.userId = userId;
    this.locations = locations;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

}
