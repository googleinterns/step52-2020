package com.google.sps.data;

// Store a places name and id (could possibly be replaced by the business class)
public class Place {

  String name;
  String placeId;
  long intervalStartUnixTimeSeconds;
  long intervalEndUnixTimeSeconds;
  
  public Place(String name, String placeId, long intervalStart, long intervalEnd) {
    this.name = name;
    this.placeId = placeId;
    intervalStartUnixTimeSeconds = intervalStart;
    intervalEndUnixTimeSeconds = intervalEnd;
  }

}
