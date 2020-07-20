package com.onlinecontacttracing.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.PositiveUserLocations;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.Constants;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import java.util.logging.Logger;
import java.time.Instant;

/**
* This class stores the information needed to query old data from a given class and delete it.
*/
public enum OldDataDeleter {
  NOTIFICATION_BATCH(NotificationBatch.class, "timeCreatedSeconds <", Constants.NOTIFICATION_BATCH_MAX_TIME),
  NEGATIVE_USER_PLACES(NegativeUserPlace.class, "place.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  NEGATIVE_USER_LOCATIONS(NegativeUserLocation.class, "location.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_PLACES(PositiveUserPlaces.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_LOCATIONS(PositiveUserLocations.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_CONTACTS(PositiveUserContacts.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME);

  Class oldDataType;
  String query;
  long maxTimeAllowedInStorage;

  OldDataDeleter(Class oldDataType, String query, long maxTimeAllowedInStorage) {
    this.oldDataType = oldDataType;
    this.query = query;
    this.maxTimeAllowedInStorage = maxTimeAllowedInStorage;
  }

  /**
  * This method deletes old data.
  * If the query was successful, the number of entries deleted will be logged.
  * Otherwise the exception is caught and logged.
  */
  public static void deleteOldData(OldDataDeleter oldDataDeleter, Logger log) {
    
    /**
    * To calculate the time for querying old data, this method
    * subtracts maximum time a class is allowed to persist in memory
    * from the current time by the 
    */
    long timeStampToGetOldData = Instant.now().getEpochSecond()-oldDataDeleter.maxTimeAllowedInStorage;

    // Fetch keys of old data
    Iterable<Key<?>> oldDataKeys = ofy().load().type(oldDataDeleter.oldDataType)
      .filter(oldDataDeleter.query, timeStampToGetOldData).keys();

    // Save data type class name to report in log
    String oldDataTypeName = oldDataDeleter.oldDataType.getSimpleName() + " class";

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