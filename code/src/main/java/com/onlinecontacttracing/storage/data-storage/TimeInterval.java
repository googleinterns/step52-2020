package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Index;

/**
* This class stores a timeinterval to be used in the Locationa and Place class
*/
@Index
public class TimeInterval {

  @Index long intervalStartSeconds;
  @Index long intervalEndSeconds;

  public TimeInterval(long intervalStart, long intervalEnd) {
    intervalStartSeconds = intervalStart;
    intervalEndSeconds = intervalEnd;
  }
  
}