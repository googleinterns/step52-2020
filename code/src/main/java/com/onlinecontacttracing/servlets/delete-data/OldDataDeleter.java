package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.Constants;
import com.google.common.collect.Iterables;
import java.time.Instant;

class OldDataDeleter {


  /**
  * This method deletes old data and returns the percentage of successful deletions
  */
  private static int deleteOldData(Class oldDataType, long MAX_TIME) {
    // Determine at what time old data exists
    long timeStampOfOldData = Instant.now().getEpochSecond()-MAX_TIME;

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(oldDataType).filter("timeCreatedSeconds <", timeStampOfOldData).keys();
    int numberOfKeysToDelete = Iterables.size(oldDataKeys);

    if (numberOfKeysToDelete != 0) {
      // Delete old data
      ofy().delete().keys(oldDataKeys);

      // Check if any old data was not deleted and return percentage of successful deletions
      oldDataKeys = ofy().load().type(oldDataType).filter("timeCreatedSeconds <", timeStampOfOldData).keys();
      int numberOfKeysLeft = Iterables.size(oldDataKeys);
    
      return 100 - (numberOfKeysLeft * 100) / numberOfKeysToDelete;
    }
    
    // Nothing to delete
    return 100;
  }

  public static int deleteOldBatches() {
    return deleteOldData(NotificationBatch.class, Constants.NOTIFICATION_BATCH_MAX_TIME);
  }

  public static int deleteOldPlaces() {
    return deleteOldData(NegativeUserPlace.class, Constants.NEGATIVE_USER_DATA_MAX_TIME);
  }

  public static int deleteOldLocations() {
    return deleteOldData(NegativeUserLocation.class, Constants.NEGATIVE_USER_DATA_MAX_TIME);
  }
}