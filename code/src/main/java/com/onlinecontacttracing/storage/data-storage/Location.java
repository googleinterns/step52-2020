package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
* This class stores a location based on geographic coordinates and
* the time interval when the user was present
*/
@Index
public class Location {

  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  long latitudeE7;
  long longitudeE7;
  @Unindex int accuracy;
  TimeInterval timeInterval;

  // Objecify requires one constructor with no parameters
  private Location() {}

  public Location(long lat, long lng, int accuracy, long intervalStart, long intervalEnd) {
    latitudeE7 = lat;
    longitudeE7 = lng;
    this.accuracy = accuracy;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
  }

  public long getLatitudeE7() {
    return latitudeE7;
  }

  public long getLongitudeE7() {
    return longitudeE7;
  }

  public double getLatitude() {
    return latitudeE7/10^7;
  }

  public double getLongitude() {
    return longitudeE7/10^7;
  }

  public long getIntervalStartSeconds() {
    return timeInterval.intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return timeInterval.intervalEndSeconds;
  }
  
  @Override  
  public String toString() {
    return "(" + latitudeE7 + "," + longitudeE7 + ") at " + timeInterval;
  }
}