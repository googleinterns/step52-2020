package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;

/**
* This class stores the information needed to identify a negative user
* at a specific place.
*/
@Entity
public class NegativeUserPlace {

  // The Id is needed for objectify but not the implementation of this class
  @Id private Long key;
  @Index private String userId;
  private Place place;

  // Objecify requires one constructor with no parameters
  private NegativeUserPlace() {}

  public NegativeUserPlace(String id, String placeId, String nameOfPlace, long intervalStart, long intervalEnd) {
    userId = id;
    place = new Place(placeId, nameOfPlace, intervalStart, intervalEnd);
  }

  public String getUserId() {
      return userId;
  }

  public String getPlaceId() {
    return place.getPlaceId();
  }

  public long getIntervalStartSeconds() {
    return place.getIntervalStartSeconds();
  }

  public long getIntervalEndSeconds() {
    return place.getIntervalEndSeconds();
  }

  @Override
  public String toString() {
    String person = "Negative User id: " + userId + "\n";
    String placedAtTime = "  " + place;
    return person + placedAtTime; 
  }

}