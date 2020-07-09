package com.google.sps.storage;

/**
* This class stores a location based on geographic coordinates and
* the time interval when the user was present
*/
public class Location {

  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  private long longitudeE7;
  private long latitudeE7;
  private long intervalStartSeconds;
  private long intervalEndSeconds;

  public Location(long lng, long lat, long intervalStart, long intervalEnd) {
    longitudeE7 = lng;
    latitudeE7 = lat;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
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