package com.google.sps.data;

import java.time.Instant;

public class BusinessNumber implements ContactStatus{

  String nameOfBussiness;
  long phoneNumber;
  private long getTimeWhenCalledUnixTimeSeconds;
  private boolean status = false;

  public BusinessNumber(String nameOfBussiness, long phoneNumber) {
    this.nameOfBussiness = nameOfBussiness;
    this.phoneNumber = phoneNumber;
  }

  public void setSuccess() {
    getTimeWhenCalledUnixTimeSeconds = Instant.now().getEpochSecond();
    status = true;
  }

  public boolean getStatus() {
    return status;
  }

  public long getTimeWhenContactedUnixTimeSeconds() {
    return getTimeWhenCalledUnixTimeSeconds;
  }
  
}
