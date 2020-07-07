package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

@Entity
public class Business {// This class is solely to check if a business was contacted in the last week.

  @Id String placeId;
  private long timeOfLastContactUnixTimeSeconds;

  private Business() {}
  
  public Business(String placeId) {
    this.placeId = placeId;
    
    // Initialize time of last contacted to be over a week so contactedInLastWeek returns false
    timeOfLastContactUnixTimeSeconds = Instant.now().getEpochSecond() - Constants.oneWeekInSeconds;
  }

  // Update timeOfContact to current time
  public void updateTimeOfContact() {
    timeOfLastContactUnixTimeSeconds = Instant.now().getEpochSecond();
  }

  //Businesses should only be contacted once a week
  public boolean contactedInLastWeek() {
    return (Instant.now().getEpochSecond() - timeOfLastContactUnixTimeSeconds) < Constants.oneWeekInSeconds;
  }
}