package com.google.sps.data;

import java.time.Instant;

public class PersonEmail implements ContactStatus{

  String nameOfSender;
  String email;
  long getTimeWhenEmailedUnixTimeSeconds;
  boolean personHasBeenEmailed;

  public PersonEmail(String nameOfSender, String email) {
    this.nameOfSender = nameOfSender;
    this.email = email;
  }
  
  public void setSuccess() {
    getTimeWhenEmailedUnixTimeSeconds = Instant.now().getEpochSecond();
    personHasBeenEmailed = true;
  }

  public boolean getStatus() {
    return personHasBeenEmailed;
  }

  public long getTimeWhenContactedUnixTimeSeconds() {
    return getTimeWhenEmailedUnixTimeSeconds;
  }
}
