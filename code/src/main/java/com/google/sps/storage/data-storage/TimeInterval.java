package com.google.sps.storage;

/**
* This class stores a timeinterval to be used in the Locationa and Place class
*/
public class TimeInterval {

  private final long intervalStartSeconds;
  private final long intervalEndSeconds;

  public TimeInterval(long intervalStart, long intervalEnd) {
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

  public long getIntervalStartSeconds() {
    return intervalStartSeconds;
  }

  public long getIntervalEndSeconds() {
    return intervalEndSeconds;
  }
  
}