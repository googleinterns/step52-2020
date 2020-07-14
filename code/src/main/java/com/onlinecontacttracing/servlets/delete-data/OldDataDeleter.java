package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.google.common.collect.Iterables;
import java.time.Instant;

class OldDataDeleter {


  /**
  * This method deletes old data and returns the percentage of successful deletions
  */
  public static int deleteOldData(OldDataTypes oldDataType) {
    // Determine at what time old data exists
    long timeStampOfOldData = Instant.now().getEpochSecond()-oldDataType.getMaxTime();

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(oldDataType.getOldDataClass()).filter("timeCreatedSeconds <", timeStampOfOldData).keys();
    int numberOfKeysToDelete = Iterables.size(oldDataKeys);

    if (numberOfKeysToDelete != 0) {
      // Delete old data
      ofy().delete().keys(oldDataKeys);

      // Check if any old data was not deleted and return percentage of successful deletions
      oldDataKeys = ofy().load().type(oldDataType.getOldDataClass()).filter("timeCreatedSeconds <", timeStampOfOldData).keys();
      int numberOfKeysLeft = Iterables.size(oldDataKeys);
    
      return 100 - (numberOfKeysLeft * 100) / numberOfKeysToDelete;
    }
    
    // Nothing to delete
    return 100;
  }
}