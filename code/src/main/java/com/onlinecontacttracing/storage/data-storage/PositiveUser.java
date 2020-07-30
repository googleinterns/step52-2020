package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.time.Instant;
import java.util.Date;

/**
* This class stores the information required to identify a positive user 
* and limit how many emails they have sent.
*/
@Entity
public class PositiveUser {

  @Id private String userId;
  private String userEmail;
  private int attemptedEmailDrafts;
  private int emailsSent;
  private long firstLoginSeconds;
  private long lastLoginSeconds;

  // Objecify requires one constructor with no parameters
  private PositiveUser() {}
  
  public PositiveUser(String id, String email) {
    userId = id;
    userEmail = email;
    attemptedEmailDrafts = 0;
    emailsSent = 0;
    firstLoginSeconds = lastLoginSeconds = Instant.now().getEpochSecond();
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
  
  public int getNumberOfEmailsSent() {
    return emailsSent;
  }

  public long getFirstLoginInSeconds() {
    return firstLoginSeconds;
  }

  public long getLastLoginInSeconds() {
    return lastLoginSeconds;
  }

  public int getAttemptedEmailDrafts() {
    return attemptedEmailDrafts;
  }

  public void incrementAttemptedEmailDrafts() {
    attemptedEmailDrafts++;
  }

  public void incrementEmailsSent() {
    emailsSent++;
  }

  public boolean userCanStillSendEmails() {
    return emailsSent < Constants.EMAILING_THRESHOLD;
  }

  @Override
  public String toString() {
    String person = String.format("(Positive User) ID: %s, email: %s\n", userId, userEmail);
    String hasNumberOfAttemptsLeft = String.format("  number of attempted drafts left: %s\n", (Constants.NUMBER_OF_DRAFTS_ALLOWED - attemptedEmailDrafts));
    String hasSentThisManyEmails = String.format("  number of emails sent: %s\n", emailsSent);

    Date firstLogin = new java.util.Date(firstLoginSeconds * 1000);
    Date lastLogin = new java.util.Date(lastLoginSeconds * 1000);
    String loginInformation = String.format("  first login: %s, last login: %s", firstLogin, lastLogin);

    return String.format("%s%s%s%s", person, hasNumberOfAttemptsLeft, hasSentThisManyEmails, loginInformation);
  }
}