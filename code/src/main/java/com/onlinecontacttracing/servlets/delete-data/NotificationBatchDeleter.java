package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.Constants;

/**
* This class deletes old notification batches
*/
class NotificationBatchDeleter extends OldDataDeleter {

  public Class getType() {
    return NotificationBatch.class;
  }

  public String getQueryString() {
    return "timeCreatedSeconds <";
  }

  /**
  * To calculate the time for querying old data, this method
  * subtracts maximum time a class is allowed to persist in memory
  * from the current time by the 
  */
  public long getMaxAge() {
    return Constants.NOTIFICATION_BATCH_MAX_TIME;
  }
}