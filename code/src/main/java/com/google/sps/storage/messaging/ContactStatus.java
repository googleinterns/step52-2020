package com.google.sps.storage;

public interface ContactStatus {
  public void setSuccess();
  public boolean getStatus();
  public long getTimeWhenContactedSeconds();
}