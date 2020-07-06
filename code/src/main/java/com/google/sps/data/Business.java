package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

@Entity
public class Business {

  @Id String placeId;
  private long timeOfLastContactUnixTimeSeconds;

  private Business() {}
  
  public Business(String placeId) {
    this.placeId = placeId;
  }

  // Update timeOfContact to current time
  public void contact() {
    timeOfLastContactUnixTimeSeconds = Instant.now().getEpochSecond();
  }

  //Businesses should only be contacted once a week
  public boolean contactedInLastWeek() {
    return (Instant.now().getEpochSecond() - timeOfLastContactUnixTimeSeconds) < Constants.oneWeekInSeconds;
  }
}
