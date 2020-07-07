package com.google.sps.data;

/* Store a location based on longitude/latitude coordinates and
 * the time interval when the user was present
 */
public class Location {

  long lng;
  long lat;
  long intervalStart;
  long intervalEnd;

  public Location(long lng, long lat, long intervalStart, long intervalEnd) {
    this.lng = lng;
    this.lat = lat;
    this.intervalStart = intervalStart;
    this.intervalEnd = intervalEnd;
  }
  
}