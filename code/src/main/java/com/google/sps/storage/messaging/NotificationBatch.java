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

  @Id String userId;
  @Index int priority; // use index to prioritize which emails are sent
  long timeCreatedSeconds; // Delete after two weeks
  ArrayList<PersonEmail> personEmails;
  ArrayList<BusinessNumber> businessNumbers;

  // Objecify requires one constructor with no parameters
  private NotificationBatch() {}

  public NotificationBatch(String userId, ArrayList<PersonEmail> personEmails, ArrayList<BusinessNumber> businessNumbers) {
    this.userId = userId;
    this.personEmails = personEmails;
    this.businessNumbers = businessNumbers;
    timeCreatedSeconds = Instant.now().getEpochSecond();
  }
  
}
