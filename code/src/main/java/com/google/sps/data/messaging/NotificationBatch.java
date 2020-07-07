package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.util.ArrayList;
import java.time.Instant;

@Entity
public class NotificationBatch {

  @Id String userId;
  @Index int priority; // use index to prioritize which emails are sent
  long timeCreatedUnixTimeSeconds; // Delete after two weeks
  ArrayList<PersonEmail> personEmails;
  ArrayList<BusinessNumber> businessNumbers;

  private NotificationBatch() {}

  public NotificationBatch(String userId, ArrayList<PersonEmail> personEmails, ArrayList<BusinessNumber> businessNumbers) {
    this.userId = userId;
    this.personEmails = personEmails;
    this.businessNumbers = businessNumbers;
    timeCreatedUnixTimeSeconds = Instant.now().getEpochSecond();
  }
  
}