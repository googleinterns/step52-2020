package com.onlinecontacttracing.storage;

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
    
  // The Id is needed for objectify but not the implementation of this class
  @Id private Long key;
  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  private Location location;
  private String  userId;

  // Objecify requires one constructor with no parameters
  private NegativeUserLocation() {}

  public NegativeUserLocation(String id, long lng, long lat, long intervalStart, long intervalEnd) {
    userId = id;
    location = new Location(lng, lat, intervalStart, intervalEnd);
  }

  public String getUserId() {
      return userId;
  }

  public long getLongitudeE7() {
    return location.longitudeE7;
  }

  public long getLatitudeE7() {
    return location.latitudeE7;
  }

  public double getLongitude() {
    return location.longitudeE7/10^7;
  }

  public double getLatitude() {
    return location.latitudeE7/10^7;
  }

  public long getIntervalStartSeconds() {
    return location.timeInterval.intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return location.timeInterval.intervalEndSeconds;
  }

}