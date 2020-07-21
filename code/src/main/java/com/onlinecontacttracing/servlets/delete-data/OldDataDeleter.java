package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import java.util.logging.Logger;
import java.time.Instant;

/**
* This class stores the information needed to query old data from a given class and delete it.
*/
abstract class OldDataDeleter {
  public abstract Class getType();
  public abstract String getQueryString();
  public abstract long getRetentionDuration();

  /**
  * This method deletes old data.
  * If the query was successful, the number of entries deleted will be logged.
  * Otherwise the exception is caught and logged.
  */
  public void delete(Logger log) {

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(getType())
      .filter(getQueryString(), Instant.now().getEpochSecond() - getRetentionDuration()).keys();

    // Save data type class name to report in log
    String oldDataTypeName = getType().getSimpleName() + " class";

    try {
      // Check how many entries will be deleted to report in log
      int numberOfDataToDelete = Iterables.size(oldDataKeys);

      ofy().delete().keys(oldDataKeys).now();

      // Log number of entries deleted
      log.info( "Deleted " + numberOfDataToDelete + " entries from the " + oldDataTypeName);

    } catch(Exception e) {
      log.info( "Deleting from the " + oldDataTypeName + " failed. The following error was found: " + e.toString());
    }
  }
} 