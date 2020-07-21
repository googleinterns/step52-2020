package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.Constants;

/**
* This class deletes old notification batches
*/
class  PositiveUserPlacesDeleter extends OldDataDeleter {

  public Class getType() {
    return PositiveUserPlaces.class;
  }

  public String getQueryString() {
    return "timeCreatedSeconds <";
  }

  /**
  * To calculate the time for querying old data, this method
  * subtracts maximum time a class is allowed to persist in memory
  * from the current time by the 
  */
  public long getRetentionDuration() {
    return Constants.POSITIVE_USER_DATA_MAX_TIME;
  }
}