package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

//tracks whether a user's message triggers any of our filter flags
interface FlaggingFilter {
  boolean passesFilter(PositiveUser positiveUser, String message);
  String errorMessageToUser();
}