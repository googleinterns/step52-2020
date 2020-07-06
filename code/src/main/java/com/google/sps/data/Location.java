package com.google.sps.data;

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