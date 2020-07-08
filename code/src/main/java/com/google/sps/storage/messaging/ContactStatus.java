package com.google.sps.storage;

/**
* Interface to help identify if a notification was successful.
*/
public interface ContactStatus {
  public void setSuccess();
  public boolean getStatus();
  public long getTimeWhenContactedSeconds();
}