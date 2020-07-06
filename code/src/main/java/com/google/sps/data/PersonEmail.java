package com.google.sps.data;

import java.time.Instant;

public class PersonEmail implements ContactStatus{

  String nameOfSender;
  String email;
  long getTimeWhenEmailedUnixTimeSeconds;
  boolean status;

  public PersonEmail(String nameOfSender, String email) {
    this.nameOfSender = nameOfSender;
    this.email = email;
  }
  
  public void setSuccess() {
    getTimeWhenEmailedUnixTimeSeconds = Instant.now().getEpochSecond();
    status = true;
  }

  public boolean getStatus() {
    return status;
  }

  public long getTimeWhenContactedUnixTimeSeconds() {
    return getTimeWhenEmailedUnixTimeSeconds;
  }
}
