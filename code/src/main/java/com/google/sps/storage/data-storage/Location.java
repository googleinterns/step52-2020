package com.google.sps.storage;

/**
* This class stores a location based on geographic coordinates and
* the time interval when the user was present
*/
public class Location extends TimeInterval {

  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  private long longitudeE7;
  private long latitudeE7;

  public Location(long lng, long lat, long intervalStart, long intervalEnd) {
    super(intervalStart, intervalEnd);
    longitudeE7 = lng;
    latitudeE7 = lat;
  }

  public long getLongitudeE7() {
    return longitudeE7;
  }

  public long getLatitudeE7() {
    return latitudeE7;
  }
  
}