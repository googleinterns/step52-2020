package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* This class stores the information needed to identify a negative user
* at a specific place.
*/
@Entity
public class NegativeUserPlace {

  // The Id is needed for objectify but not the implementation of this class
  @Id private Long key;
  @Index private String placeId;
  @Index private long intervalStartSeconds;
  @Index private long intervalEndSeconds;
  private String nameOfPlace;
  private String userId;

  // Objecify requires one constructor with no parameters
  private NegativeUserPlace() {}

  public NegativeUserPlace(String userId, String placeId, long intervalStart, long intervalEnd, String name) {
    this.userId = userId;
    this.placeId = placeId;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
    nameOfPlace = name;
  }

  public String getUserId() {
      return userId;
  }

  public String getName() {
    return nameOfPlace;
  }

  public String getPlaceId() {
    return placeId;
  }

  public long getIntervalStartSeconds() {
    return intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return intervalEndSeconds;
  }

}