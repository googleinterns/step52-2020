package com.google.sps.storage;

import java.time.Instant;
import java.util.Optional;

/**
* This class keeps track of information needed to contact people and businesses.
*/
public class PersonEmail implements ContactStatus{

  private final String nameOfSender;
  private final String email;
  private Optional<Long> getTimeWhenEmailedSeconds;
  private boolean personHasBeenEmailed;

  public PersonEmail(String nameOfSender, String email) {
    this.nameOfSender = nameOfSender;
    this.email = email;
    getTimeWhenEmailedSeconds = Optional.empty();
    personHasBeenEmailed = false;
  }
  
  @Override
  public void markContactedSuccessfully() {
    getTimeWhenEmailedSeconds = Optional.of(Instant.now().getEpochSecond());
    personHasBeenEmailed = true;
  }

  @Override
  public boolean hasBeenContactedSuccessfully() {
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