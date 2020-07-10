package com.google.sps.storage;

/**
* This class stores a location based on geographic coordinates and
* the time interval when the user was present
*/
public class Location {

  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  private final long longitudeE7;
  private final long latitudeE7;
  private final TimeInterval timeInterval;

  public Location(long lng, long lat, long intervalStart, long intervalEnd) {
    longitudeE7 = lng;
    latitudeE7 = lat;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
  }

  public long getLongitudeE7() {
    return longitudeE7;
  }

  public long getLatitudeE7() {
    return latitudeE7;
  }
  
}