package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* This class stores the information needed to identify a negative user
* at a specific location (geographic coordinate)
*/
@Entity
public class NegativeUserLocation {

  private String  userId;
  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  @Index private long longitudeE7;
  @Index private long latitudeE7;
  @Index private long intervalStartSeconds;
  @Index private long intervalEndSeconds;
  @Id private Long key;

  // Objecify requires one constructor with no parameters
  private NegativeUserLocation() {}

  public NegativeUserLocation(String id, long lng, long lat, long intervalStart, long intervalEnd) {
    userId = id;
    longitudeE7 = lng;
    latitudeE7 = lat;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

  public String getUserId() {
      return userId;
  }

  public long getLongitudeE7() {
    return longitudeE7;
  }

  public long getLatitudeE7() {
    return latitudeE7;
  }

  public long getIntervalStartSeconds() {
    return intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return intervalEndSeconds;
  }

}