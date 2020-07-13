package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;

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

  public NegativeUserLocation(String id, long lat, long lng, int accuracy, long intervalStart, long intervalEnd) {
    userId = id;
    location = new Location(lat, lng, accuracy, intervalStart, intervalEnd);
  }

  public String getUserId() {
      return userId;
  }

  public long getLatitudeE7() {
    return location.getLatitudeE7();
  }

  public long getLongitudeE7() {
    return location.getLongitudeE7();
  }

  public double getLatitude() {
    return location.getLatitude();
  }

  public double getLongitude() {
    return location.getLongitude();
  }

  public long getIntervalStartSeconds() {
    return location.getIntervalStartSeconds();
  }

  public long getIntervalEndSeconds() {
    return location.getIntervalEndSeconds();
  }

  @Override
  public String toString() {
    String person = "Negative User id: " + userId + "\n";
    String locatedAtTime = "  " + location;
    return person + locatedAtTime; 
  } 
}