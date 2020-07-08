package com.google.sps.storage;

import java.time.Instant;

/**
* Keep track of information needed to contact a business
*/
public class BusinessNumber implements ContactStatus{

  String nameOfBusiness;
  String phoneNumber;
  private long getTimeWhenCalledSeconds;
  private boolean businessHasBeenCalled;

  public BusinessNumber(String nameOfBusiness, String phoneNumber) {
    this.nameOfBusiness = nameOfBusiness;
    this.phoneNumber = phoneNumber;
    businessHasBeenCalled = false;
  }

  // Record time bussiness was contacted
  @Override
  public void setSuccess() {
    getTimeWhenCalledSeconds = Instant.now().getEpochSecond();
    businessHasBeenCalled = true;
  }

  @Override
  public boolean getStatus() {
    return businessHasBeenCalled;
  }

  // Return time bussiness was contacted to display to user
  @Override
  public long getTimeWhenContactedSeconds() {
    return getTimeWhenCalledSeconds;
  }
  
}
