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
  NOTIFICATION_BATCH(NotificationBatch.class, "timeCreatedSeconds <", Constants.NOTIFICATION_BATCH_MAX_TIME),
  NEGATIVE_USER_PLACES(NegativeUserPlace.class, "place.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  NEGATIVE_USER_LOCATIONS(NegativeUserLocation.class, "location.timeInterval.intervalStartSeconds <", Constants.NEGATIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_PLACES(PositiveUserPlaces.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_LOCATIONS(PositiveUserLocations.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_CONTACTS(PositiveUserContacts.class, "timeCreatedSeconds <", Constants.POSITIVE_USER_DATA_MAX_TIME);

  Class oldDataType;
  String query;
  long maxTimeOfData;

  OldDataTypes(Class oldDataType, String query, long maxTimeOfData) {
    this.oldDataType = oldDataType;
    this.query = query;
    this.maxTimeOfData = maxTimeOfData;
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