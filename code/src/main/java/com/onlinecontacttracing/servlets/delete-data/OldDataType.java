package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.PositiveUserLocations;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.Constants;
import java.time.Instant;

/**
* This class stores the information needed to query old data from a given class.
*/
public enum OldDataType {
  NOTIFICATION_BATCH(NotificationBatch.class, "timeCreatedSeconds <", Constants.NOTIFICATION_BATCH_MAX_TIME),
  NEGATIVE_USER_PLACES(NegativeUserPlace.class, "place.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  NEGATIVE_USER_LOCATIONS(NegativeUserLocation.class, "location.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_PLACES(PositiveUserPlaces.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_LOCATIONS(PositiveUserLocations.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_CONTACTS(PositiveUserContacts.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME);

  Class oldDataType;
  String query;
  long maxTimeAllowedInStorage;

  OldDataType(Class oldDataType, String query, long maxTimeAllowedInStorage) {
    this.oldDataType = oldDataType;
    this.query = query;
    this.maxTimeAllowedInStorage = maxTimeAllowedInStorage;
  }

  public Class getOldDataClass() {
    return oldDataType;
  }

  /**
  * Subrtract the current time by the maximum time a class is allowed to persist in memory 
  * so that the query will fetch old data.
  */
  public long getMaxTimeAllowedInStorage() {
    return Instant.now().getEpochSecond()-maxTimeAllowedInStorage;
  }

  public String getQuery() {
    return query;
  }

  public String getNameOfDataType() {
    return oldDataType.getSimpleName();
  }
}