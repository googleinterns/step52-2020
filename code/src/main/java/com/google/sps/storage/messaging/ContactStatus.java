package com.google.sps.storage;

import java.util.Optional;

/**
* This interface helps identify if a notification was successful.
*/
public interface ContactStatus {
  public void markContactedSuccessfully();
  public boolean hasBeenContactedSuccessfully();
  public Optional<Long> getTimeWhenContactedSeconds();
}