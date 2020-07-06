package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.ArrayList;

@Entity
public class PositiveUserPlaces {

  @Id String userId;
  ArrayList<Place> places;
  long timeCreated;

  private PositiveUserPlaces() {}

  public PositiveUserPlaces(String userId, ArrayList<Place> places) {
    this.userId = userId;
    this.places = places;
    timeCreated = Instant.now().getEpochSecond();
  }

}
