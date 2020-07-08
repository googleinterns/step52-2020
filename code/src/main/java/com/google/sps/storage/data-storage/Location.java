package com.google.sps.storage;

/**
* Store a location based on longitude/latitude coordinates and
* the time interval when the user was present
*/
public class Location {

  long longitudeE7;
  long latitudeE7;
  long intervalStartSeconds;
  long intervalEndSeconds;

  public Location(long lng, long lat, long intervalStart, long intervalEnd) {
    longitudeE7 = lng;
    latitudeE7 = lat;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }
  
}