package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.PositiveUserLocations;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.Constants;

public enum OldDataTypes {
  NOTIFICATION_BATCH(NotificationBatch.class, Constants.NOTIFICATION_BATCH_MAX_TIME),
  NEGATIVE_USER_PLACES(NegativeUserPlace.class, Constants.NEGATIVE_USER_DATA_MAX_TIME),
  NEGATIVE_USER_LOCATIONS(NegativeUserLocation.class, Constants.NEGATIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_PLACES(PositiveUserPlaces.class, Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_LOCATIONS(PositiveUserLocations.class, Constants.POSITIVE_USER_DATA_MAX_TIME),
  POSITIVE_USER_CONTACTS(PositiveUserContacts.class, Constants.POSITIVE_USER_DATA_MAX_TIME);

  Class oldDataType;
  long maxTimeOfData;

  OldDataTypes(Class oldDataType, long maxTimeOfData) {
    this.oldDataType = oldDataType;
    this.maxTimeOfData = maxTimeOfData;
  }

  public Class getOldDataClass() {
    return oldDataType;
  }

  public long getMaxTime() {
    return maxTimeOfData;
  }
}