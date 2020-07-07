package com.google.sps.storage;

// Store a places name and id (could possibly be replaced by the business class)
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
