package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class PositiveUserLocations {

  @Id String userId;
  ArrayList<Location> locations;
  long timeCreated;

  private PositiveUserLocations() {}

  public PositiveUserLocations(String userId, ArrayList<Location> locations) {
    this.userId = userId;
    this.locations = locations;
    timeCreated = Instant.now().getEpochSecond();
  }

}
