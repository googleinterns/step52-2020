package com.google.sps.storage;

import java.time.Instant;

public class PersonEmail implements ContactStatus{

  String nameOfSender;
  String email;
  long getTimeWhenEmailedSeconds;
  boolean personHasBeenEmailed;

  public PersonEmail(String nameOfSender, String email) {
    this.nameOfSender = nameOfSender;
    this.email = email;
  }
  
  @Override
  public void setSuccess() {
    getTimeWhenEmailedSeconds = Instant.now().getEpochSecond();
    personHasBeenEmailed = true;
  }

  @Override
  public boolean getStatus() {
    return personHasBeenEmailed;
  }

  @Override
  public long getTimeWhenContactedSeconds() {
    return getTimeWhenEmailedSeconds;
  }
}
