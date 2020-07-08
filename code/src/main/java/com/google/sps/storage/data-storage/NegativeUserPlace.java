package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;

/**
* This class stores the information needed to identify a negative user
* at a specific place.
*/
@Entity
public class NegativeUserPlace {

  @Index String placeId;
  String nameOfPlace;
  String userId;
  @Index long intervalStartSeconds;
  @Index long intervalEndSeconds;
  @Id int key;

  // Objecify requires one constructor with no parameters
  private NegativeUserPlace() {}

  public NegativeUserPlace(String userId, String name, String placeId, long intervalStart, long intervalEnd) {
    this.placeId = placeId;
    this.nameOfPlace = name;
    this.userId = userId;
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

}
