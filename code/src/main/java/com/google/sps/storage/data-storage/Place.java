package com.google.sps.storage;

/**
* This class keeps track of a place's name and id
*/
public class Place extends TimeInterval {

  private String nameOfPlace;
  private String placeId;
  
  public Place(String name, String id, long intervalStart, long intervalEnd) {
    super(intervalStart, intervalEnd);
    nameOfPlace = name;
    placeId = id;
  }

  public String getName() {
    return nameOfPlace;
  }

  public String getPlaceId() {
    return placeId;
  }

}