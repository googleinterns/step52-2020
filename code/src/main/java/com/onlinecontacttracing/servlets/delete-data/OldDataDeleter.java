package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.google.common.collect.Iterables;
import java.util.logging.Logger;

/**
* This class handles the logic for deleting data.
*/
class OldDataDeleter {

  /**
  * This method deletes old data.
  * If the query was successful, the number of entries deleted will be logged.
  * Otherwise the exception is caught and logged.
  */
  public static void deleteOldData(OldDataType oldDataType, Logger log) {
    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(oldDataType.getOldDataClassType())
      .filter(oldDataType.getQuery(), oldDataType.getTimeOfOldData()).keys();

    // Save data type class name to report in log
    String oldDataTypeName = oldDataType.getNameOfDataType() + " class";

    try {
      // Check how many entries will be deleted
      int numberOfDataToDelete = Iterables.size(oldDataKeys);

      // Delete old data
      ofy().delete().keys(oldDataKeys);
      
      // Log number of entries deleted
      log.info( "Deleted " + numberOfDataToDelete + " entries from the " + oldDataTypeName);

    } catch(Exception e) {
      // Log error
      log.info( "Deleting from the " + oldDataTypeName + " failed. The following error was found: " + e.toString());
    }
  }
}