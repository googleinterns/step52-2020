package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

@Entity
public class PositiveUser {

  @Id String userId;
  private int emailsSent = 0;
  private long firstLogin;
  private long lastLogin;

  private PositiveUser() {}
  
  public PositiveUser(String userId) {
    this.userId = userId;
    firstLogin = lastLogin = Instant.now().getEpochSecond();
  }

  public void setLastLogin() {
    firstLogin = Instant.now().getEpochSecond();
  }
  
  public long getNumberOfEmailsSent() {
      return emailsSent;
  }

  public long getFirstLogin() {
    return firstLogin;
  }

  public long getLastLogin() {
    return lastLogin;
  }

  public void updateEmailsSent() {
    emailsSent++;
  }
}
