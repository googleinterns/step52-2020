package com.google.sps.data;

import java.time.Instant;

// Keep track of information needed to contact a business
public class BusinessNumber implements ContactStatus{

  String nameOfBusiness;
  long phoneNumber;
  private long getTimeWhenCalledUnixTimeSeconds;
  private boolean businessHasBeenCalled = false;

  public BusinessNumber(String nameOfBusiness, long phoneNumber) {
    this.nameOfBusiness = nameOfBusiness;
    this.phoneNumber = phoneNumber;
  }

  // Record time bussiness was contacted
  @Override
  public void setSuccess() {
    getTimeWhenCalledUnixTimeSeconds = Instant.now().getEpochSecond();
    businessHasBeenCalled = true;
  }

  @Override
  public boolean getStatus() {
    return businessHasBeenCalled;
  }

  // Return time bussiness was contacted to display to user
  @Override
  public long getTimeWhenContactedUnixTimeSeconds() {
    return getTimeWhenCalledUnixTimeSeconds;
  }
  
}
