package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class NegativeUserLocations {

  @Id String userId;
  ArrayList<Location> locations;
  long timeCreatedUnixTimeSeconds;

  private NegativeUserLocations() {}

  public NegativeUserLocations(String userId, ArrayList<Location> locations) {
    this.userId = userId;
    this.locations = locations;
    timeCreatedUnixTimeSeconds = Instant.now().getEpochSecond();
  }

}
