package com.google.sps.data;

import java.time.Instant;

// Keep track of information needed to contact a business
public class BusinessNumber implements ContactStatus{

  String nameOfBussiness;
  long phoneNumber;
  private long getTimeWhenCalledUnixTimeSeconds;
  private boolean businessHasBeenCalled = false;

  public BusinessNumber(String nameOfBussiness, long phoneNumber) {
    this.nameOfBussiness = nameOfBussiness;
    this.phoneNumber = phoneNumber;
  }

  // Record time bussiness was contacted
  public void setSuccess() {
    getTimeWhenCalledUnixTimeSeconds = Instant.now().getEpochSecond();
    businessHasBeenCalled = true;
  }

  public boolean getStatus() {
    return businessHasBeenCalled;
  }

  // Return time bussiness was contacted to display to user
  public long getTimeWhenContactedUnixTimeSeconds() {
    return getTimeWhenCalledUnixTimeSeconds;
  }
  
}
