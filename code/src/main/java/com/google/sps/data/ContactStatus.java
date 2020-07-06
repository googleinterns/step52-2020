package com.google.sps.data;

public interface ContactStatus {
  public void setSuccess();
  public boolean getStatus();
  public long getTimeWhenContactedUnixTimeSeconds();
}