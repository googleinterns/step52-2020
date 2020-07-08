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

  String userId;
  @Index long longitudeE7;
  @Index long latitudeE7;
  @Index long intervalStartSeconds;
  @Index long intervalEndSeconds;
  @Id int key;

  // Objecify requires one constructor with no parameters
  private NegativeUserLocation() {}

  public NegativeUserLocation(String userId, long lng, long lat, long intervalStart, long intervalEnd) {
    this.userId = userId;
    longitudeE7 = lng;
    latitudeE7 = lat;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

}
