package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Index;
import java.util.Date;
import java.time.ZoneOffset; 

/**
* This class stores a timeinterval to be used in the Locationa and Place class
*/
@Index
public class TimeInterval {

  long intervalStartSeconds;
  long intervalEndSeconds;

  // Objecify requires one constructor with no parameters
  private TimeInterval() {}

  public TimeInterval(long intervalStart, long intervalEnd) {
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }

  public String displayTimeIntervalAsDate(ZoneOffset zoneOffset) {
    return String.format("%s to %s", convertToDate(intervalStartSeconds, zoneOffset), convertToDate(intervalEndSeconds, zoneOffset));
  }

  private static String convertToDate(long timeInSeconds, ZoneOffset zoneOffset) {
    long offsetTimeInMillis = (timeInSeconds - zoneOffset.getTotalSeconds()) * 1000;
    return new Date(offsetTimeInMillis).toLocaleString();
  }

  @Override  
  public String toString() {
    Date start = new java.util.Date(intervalStartSeconds * 1000);
    Date end = new java.util.Date(intervalEndSeconds * 1000);
    return start + " to " + end;
  }
}