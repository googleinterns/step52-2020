package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;

/**
* This class stores the information required to identify a Negative user 
* and send them an email.
*/
@Entity
public class NegativeUser {

  @Id private String userId;
  private String userEmail;
  private long firstLoginSeconds;
  private long lastLoginSeconds;

  // Objecify requires one constructor with no parameters
  private NegativeUser() {}
  
  public NegativeUser(String id, String email) {
    userId = id;
    userEmail = email;
    firstLoginSeconds = Instant.now().getEpochSecond();
    lastLoginSeconds = firstLoginSeconds;
  }

  public void setLastLogin() {
    lastLoginSeconds = Instant.now().getEpochSecond();
  }
  
  public String getUserId() {
    return userId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public long getFirstLoginInSeconds() {
    return firstLoginSeconds;
  }

  public long getLastLoginInSeconds() {
    return lastLoginSeconds;
  }
  
  @Override
  public String toString() {
    return String.format("(Negative User) ID: %s, email: %s\n", userId, userEmail);
  }
}