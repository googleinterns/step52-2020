package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.Constants;

/**
* This class deletes old negative user places
*/
class NegativeUserPlacesDeleter extends OldDataDeleter {

  public Class getType() {
    return NegativeUserPlace.class;
  }

  public String getQueryString() {
    return "place.timeInterval.intervalStartSeconds <";
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