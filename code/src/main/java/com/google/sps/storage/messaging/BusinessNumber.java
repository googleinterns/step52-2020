package com.google.sps.storage;

import java.time.Instant;
import java.util.Optional;

/**
* This class keeps track of information needed to contact a business
*/
public class BusinessNumber implements ContactStatus{

  private final String nameOfBusiness;
  private final String phoneNumber;
  private Optional<Long> getTimeWhenCalledSeconds;
  private boolean businessHasBeenCalled;

  public BusinessNumber(String nameOfBusiness, String phoneNumber) {
    this.nameOfBusiness = nameOfBusiness;
    this.phoneNumber = phoneNumber;
    getTimeWhenCalledSeconds = Optional.empty();
    businessHasBeenCalled = false;
  }

  /**
  * Record the time a bussiness was contacted.
  */
  @Override
  public void markContactedSuccessfully() {
    getTimeWhenCalledSeconds = Optional.of(Instant.now().getEpochSecond());
    businessHasBeenCalled = true;
  }

  @Override
  public boolean hasBeenContactedSuccessfully() {
    return businessHasBeenCalled;
  }

  /**
  * Return time a bussiness was contacted to display to user.
  */
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