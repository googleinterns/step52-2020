package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

/**
* This class stores the information required to identify a positive user 
* and limit how many emails they have sent.
*/
@Entity
public class PositiveUser {

  @Id private String userId;
  private String userEmail;
  private int emailsSent;
  private long firstLoginSeconds;
  private long lastLoginSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUser() {}
  
  public PositiveUser(String id, String email) {
    userId = id;
    userEmail = email;
    emailsSent = 0;
    firstLoginSeconds = lastLoginSeconds = Instant.now().getEpochSecond();
  }

  public void setLastLogin() {
    firstLoginSeconds = Instant.now().getEpochSecond();
  }
  
  public String getUserId() {
    return userId;
  }

  public String getUserEmail() {
    return userEmail;
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

  public boolean userCanStillSendEmails() {
    return emailsSent < Constants.EMAILING_THRESHOLD;
  }
}