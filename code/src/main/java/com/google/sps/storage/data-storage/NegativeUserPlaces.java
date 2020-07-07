package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class NegativeUserPlaces {

  @Id String userId;
  ArrayList<Place> places;
  long timeCreatedSeconds;

  private NegativeUserPlaces() {}

  public NegativeUserPlaces(String userId, ArrayList<Place> places) {
    this.userId = userId;
    this.places = places;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

}
