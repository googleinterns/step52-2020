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

  private String userId;
  @Index private String placeId;
  private String nameOfPlace;
  @Index private long intervalStartSeconds;
  @Index private long intervalEndSeconds;
  @Id private Long key;

  // Objecify requires one constructor with no parameters
  private NegativeUserPlace() {}

  public NegativeUserPlace(String userId, String name, String placeId, long intervalStart, long intervalEnd) {
    this.userId = userId;
    this.placeId = placeId;
    nameOfPlace = name;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
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
