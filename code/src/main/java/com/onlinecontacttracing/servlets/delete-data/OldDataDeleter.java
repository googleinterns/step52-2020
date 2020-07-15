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
    long timeStampToGetOldData = Instant.now().getEpochSecond()-oldDataType.getMaxTime();

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(oldDataType.getOldDataClass()).filter(oldDataType.getQuery(), timeStampToGetOldData).keys();
    int numberOfDataToDelete = Iterables.size(oldDataKeys);

    if (numberOfDataToDelete != 0) {
      // Delete old data
      ofy().delete().keys(oldDataKeys);
    }
    
    return numberOfDataToDelete;
  }
}