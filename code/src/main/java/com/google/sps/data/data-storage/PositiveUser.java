package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

@Entity
public class PositiveUser {

  @Id String userId;
  private int emailsSent = 0;
  private long firstLoginUnixTimeSeconds;
  private long lastLoginUnixTimeSeconds;

  private PositiveUser() {}
  
  public PositiveUser(String userId) {
    this.userId = userId;
    firstLoginUnixTimeSeconds = lastLoginUnixTimeSeconds = Instant.now().getEpochSecond();
  }

  public void setLastLogin() {
    firstLoginUnixTimeSeconds = Instant.now().getEpochSecond();
  }
  
  public long getNumberOfEmailsSent() {
      return emailsSent;
  }

  public long getFirstLoginInUnixTimeSeconds() {
    return firstLoginUnixTimeSeconds;
  }

  public long getLastLoginInUnixTimeSeconds() {
    return lastLoginUnixTimeSeconds;
  }

  public void incrementEmailsSent() {
    emailsSent++;
  }
}
