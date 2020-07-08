package com.google.sps.storage;

/**
* Store a place's name and id
*/
public class Place {

  private String nameOfPlace;
  private String placeId;
  private long intervalStartSeconds;
  private long intervalEndSeconds;
  
  public Place(String name, String id, long intervalStart, long intervalEnd) {
    nameOfPlace = name;
    placeId = id;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

  public String getName() {
    return nameOfPlace;
  }

  public String getPlaceId() {
    return placeId;
  }

  public long getIntervalStartSeconds() {
    return intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return intervalEndSeconds;
  }

}
