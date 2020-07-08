package com.google.sps.storage;

/**
* Store a location based on longitude/latitude coordinates and
* the time interval when the user was present
*/
public class Location {

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

  public long getLongitude() {
    return longitudeE7;
  }

  public long getLatitude() {
    return latitudeE7;
  }

  public long getIntervalStartSeconds() {
    return intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return intervalEndSeconds;
  }
  
}