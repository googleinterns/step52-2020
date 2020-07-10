package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Index;

/**
* This class keeps track of a place's name and id
*/
@Index
public class Place {

  @Index String placeId;
  TimeInterval timeInterval;
  String nameOfPlace;
  
  public Place(String id, long intervalStart, long intervalEnd, String name) {
    placeId = id;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
    nameOfPlace = name;
  }

  public String getName() {
    return nameOfPlace;
  }

  public String getPlaceId() {
    return placeId;
  }

}