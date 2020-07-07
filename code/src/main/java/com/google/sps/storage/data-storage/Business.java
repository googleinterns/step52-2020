package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

@Entity
public class Business {// This class is solely to check if a business was contacted in the last week.

  @Id String placeId;
  private long timeOfLastContactSeconds;

  private Business() {}
  
  public Business(String placeId) {
    this.placeId = placeId;
    
    // Initialize time of last contacted to be over a week so contactedInLastWeek returns false
    timeOfLastContactSeconds = Instant.now().getEpochSecond() - Constants.oneWeekInSeconds;
  }

  // Update timeOfContact to current time
  public void updateTimeOfContact() {
    timeOfLastContactSeconds = Instant.now().getEpochSecond();
  }

  //Businesses should only be contacted once a week
  public boolean contactedInLastWeek() {
    return (Instant.now().getEpochSecond() - timeOfLastContactSeconds) < Constants.oneWeekInSeconds;
  }
}