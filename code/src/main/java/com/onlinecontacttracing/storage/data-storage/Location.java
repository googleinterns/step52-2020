package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Index;

/**
* This class stores a location based on geographic coordinates and
* the time interval when the user was present
*/
@Index
public class Location {

  // Location History provides geographic coordinates multiplied by 10^7 for more accuracy
  @Index long longitudeE7;
  @Index long latitudeE7;
  int accuracy;
  TimeInterval timeInterval;

  public Location(long lng, long lat, int accuracy, long intervalStart, long intervalEnd) {
    longitudeE7 = lng;
    latitudeE7 = lat;
    this.accuracy = accuracy;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
  }
  
}