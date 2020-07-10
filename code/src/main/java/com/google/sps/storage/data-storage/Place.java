package com.google.sps.storage;

/**
* This class keeps track of a place's name and id
*/
public class Place {

  private final String nameOfPlace;
  private final String placeId;
  private final TimeInterval timeInterval;
  
  public Place(String name, String id, long intervalStart, long intervalEnd) {
    nameOfPlace = name;
    placeId = id;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
  }

  public String getName() {
    return nameOfPlace;
  }

  public String getPlaceId() {
    return placeId;
  }

}