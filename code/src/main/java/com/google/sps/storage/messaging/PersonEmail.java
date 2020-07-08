package com.google.sps.storage;

import java.time.Instant;
import java.util.Optional;

/**
* Keep track of information needed to contact a person
*/
public class PersonEmail implements ContactStatus{

  private String nameOfSender;
  private String email;
  private Optional<Long> getTimeWhenEmailedSeconds;
  private boolean personHasBeenEmailed;

  public PersonEmail(String nameOfSender, String email) {
    this.nameOfSender = nameOfSender;
    this.email = email;
    getTimeWhenEmailedSeconds = Optional.empty();
    personHasBeenEmailed = false;
  }
  
  @Override
  public void setSuccess() {
    getTimeWhenEmailedSeconds = Optional.of(Instant.now().getEpochSecond());
    personHasBeenEmailed = true;
  }

  @Override
  public boolean getStatus() {
    return personHasBeenEmailed;
  }

  @Override
  public Optional<Long> getTimeWhenContactedSeconds() {
    return getTimeWhenEmailedSeconds;
  }

  public String getName() {
    return nameOfSender;
  }

  public String getEmail() {
    return email;
  }
}
