package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.util.ArrayList;
import java.time.Instant;

/**
* A NotificationBatch is a collection of emails and phone numbers that need to be contacted.
*/
@Entity
public class NotificationBatch {

  @Id private String userId;
  @Index private long timeCreatedSeconds; // Delete after two weeks
  private ArrayList<PersonEmail> personEmails;
  private ArrayList<BusinessNumber> businessNumbers;

  // Objecify requires one constructor with no parameters
  private NotificationBatch() {}

  public NotificationBatch(String userId, ArrayList<PersonEmail> personEmails, ArrayList<BusinessNumber> businessNumbers) {
    this.userId = userId;
    this.personEmails = personEmails;
    this.businessNumbers = businessNumbers;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }

  public String getUserId() {
      return userId;
  }

  public long getUserTimeCreatedSeconds() {
      return timeCreatedSeconds;
  }

  public ArrayList<PersonEmail> getPersonEmails() {
      return personEmails;
  }

  public ArrayList<BusinessNumber> getBusinessNumbers() {
      return businessNumbers;
  }
  
}
