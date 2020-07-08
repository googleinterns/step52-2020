package com.google.sps.storage;

/**
* Store a place's name and id
*/
public class Place {

  String name;
  String placeId;
  long intervalStartSeconds;
  long intervalEndSeconds;
  
  public Place(String name, String placeId, long intervalStart, long intervalEnd) {
    this.name = name;
    this.placeId = placeId;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

}
