package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.Constants;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import java.util.logging.Logger;
import java.time.Instant;

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
  public long getMaxAge() {
    return Instant.now().getEpochSecond() - Constants.NEGATIVE_USER_DATA_MAX_TIME;
  }

  /**
  * This method deletes old data.
  * If the query was successful, the number of entries deleted will be logged.
  * Otherwise the exception is caught and logged.
  */
  public void delete(Logger log) {

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(getType())
      .filter(getQueryString(), getMaxAge()).keys();

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