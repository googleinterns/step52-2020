package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.Constants;

/**
* This class deletes old negative user locations
*/
class NegativeUserLocationsDeleter extends OldDataDeleter {

  public Class getType() {
    return NegativeUserLocation.class;
  }

  public String getQueryString() {
    return "location.timeInterval.intervalStartSeconds <";
  }

  /**
  * To calculate the time for querying old data, this method
  * subtracts maximum time a class is allowed to persist in memory
  * from the current time by the 
  */
  public long getRetentionDuration() {
    return Constants.NEGATIVE_USER_DATA_MAX_TIME;
  }
}