package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.Optional;

/**
* This class is used to check if a business was contacted in the last week.
*/
@Entity
public class Business {

  @Id private String placeId;
  private Optional<Long> timeOfLastContactSeconds;

  // Objecify requires one constructor with no parameters
  private Business() {}
  
  public Business(String placeId) {
    // Geocoding API provides a unique identifier called placeId that we can use to identify Businesses' at a local level
    this.placeId = placeId;
    timeOfLastContactSeconds = Optional.empty();
  }

  /**
  * This methods updates timeOfLastContactSeconds to the current time
  * It should be used after calling a business so that it is not re-contacted within a week
  */
  public void updateTimeOfContact() {
    timeOfLastContactSeconds = Optional.of(Instant.now().getEpochSecond());
  }

  /**
  * This methods checks if a business was contacted in the last week.
  * It should be checked before scheduling a call.
  */
  public boolean stillInCooldownPeriodFromLastContact() {
    return timeOfLastContactSeconds.filter(timeOfLastContact -> (Instant.now().getEpochSecond() - timeOfLastContact) < Constants.BUSINESS_CONTACT_COOLDOWN_SECONDS).isPresent();
  }
}