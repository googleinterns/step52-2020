package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

/**
* Store the information associated with positive user.
*/
@Entity
public class PositiveUser {

  @Id String userId;
  String userEmail;
  private int emailsSent = 0;
  private long firstLoginSeconds;
  private long lastLoginSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUser() {}
  
  public PositiveUser(String userId) {
    this.userId = userId;
    firstLoginSeconds = lastLoginSeconds = Instant.now().getEpochSecond();
  }

  public void setLastLogin() {
    firstLoginSeconds = Instant.now().getEpochSecond();
  }
  
  public long getNumberOfEmailsSent() {
      return emailsSent;
  }

  public long getFirstLoginInSeconds() {
    return firstLoginSeconds;
  }

  public long getLastLoginInSeconds() {
    return lastLoginSeconds;
  }

  public void incrementEmailsSent() {
    emailsSent++;
  }
}
