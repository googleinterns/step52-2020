package com.google.sps.storage;

import java.time.Instant;
import java.util.Optional;

/**
* Keep track of information needed to contact a business
*/
public class BusinessNumber implements ContactStatus{

  private String nameOfBusiness;
  private String phoneNumber;
  private Optional<Long> getTimeWhenCalledSeconds;
  private boolean businessHasBeenCalled;

  public BusinessNumber(String nameOfBusiness, String phoneNumber) {
    this.nameOfBusiness = nameOfBusiness;
    this.phoneNumber = phoneNumber;
    getTimeWhenCalledSeconds = Optional.empty();
    businessHasBeenCalled = false;
  }

  // Record time bussiness was contacted
  @Override
  public void setSuccess() {
    getTimeWhenCalledSeconds = Optional.of(Instant.now().getEpochSecond());
    businessHasBeenCalled = true;
  }

  @Override
  public boolean getStatus() {
    return businessHasBeenCalled;
  }

  // Return time bussiness was contacted to display to user
  @Override
  public Optional<Long> getTimeWhenContactedSeconds() {
    return getTimeWhenCalledSeconds;
  }

  public String getName() {
    return nameOfBusiness;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  
}
