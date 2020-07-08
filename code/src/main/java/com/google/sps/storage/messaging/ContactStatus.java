package com.google.sps.storage;

import java.util.Optional;

/**
* Interface to help identify if a notification was successful.
*/
public interface ContactStatus {
  public void setSuccess();
  public boolean getStatus();
  public Optional<Long> getTimeWhenContactedSeconds();
}