package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
* This class keeps track of a place's name and id
*/
@Index
public class Place {

  @Id String placeId;
  @Unindex String nameOfPlace;
  TimeInterval timeInterval;

  // Objecify requires one constructor with no parameters
  private Place() {}

  public Place(String id, String name, long intervalStart, long intervalEnd) {
    placeId = id;
    nameOfPlace = name;
    timeInterval = new TimeInterval(intervalStart, intervalEnd);
  }

  public String getName() {
    return nameOfPlace;
  }

  @Override  
  public String toString() {
    return nameOfPlace + " (" + placeId + ") at " + timeInterval;
  }

}