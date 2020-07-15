package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.PositiveUserLocations;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.Constants;

/**
* This class stores the information needed to query old data from a given class.
*/
public enum OldDataTypes {
  NOTIFICATION_BATCH(NotificationBatch.class, Constants.NOTIFICATION_BATCH_MAX_TIME, "timeCreatedSeconds <"),
  NEGATIVE_USER_PLACES(NegativeUserPlace.class, Constants.NEGATIVE_USER_DATA_MAX_TIME, "place.timeInterval.intervalStartSeconds <"),
  NEGATIVE_USER_LOCATIONS(NegativeUserLocation.class, Constants.NEGATIVE_USER_DATA_MAX_TIME, "location.timeInterval.intervalStartSeconds <"),
  POSITIVE_USER_PLACES(PositiveUserPlaces.class, Constants.POSITIVE_USER_DATA_MAX_TIME, "timeCreatedSeconds <"),
  POSITIVE_USER_LOCATIONS(PositiveUserLocations.class, Constants.POSITIVE_USER_DATA_MAX_TIME, "timeCreatedSeconds <"),
  POSITIVE_USER_CONTACTS(PositiveUserContacts.class, Constants.POSITIVE_USER_DATA_MAX_TIME, "timeCreatedSeconds <");

  Class oldDataType;
  long maxTimeOfData;
  String query;

  OldDataTypes(Class oldDataType, long maxTimeOfData, String query) {
    this.oldDataType = oldDataType;
    this.maxTimeOfData = maxTimeOfData;
    this.query = query;
  }

  public Class getOldDataClass() {
    return oldDataType;
  }

  public long getMaxTime() {
    return maxTimeOfData;
  }

  public String getQuery() {
    return query;
  }

  public String getNameOfDataType() {
    return oldDataType.getSimpleName();
  }
}