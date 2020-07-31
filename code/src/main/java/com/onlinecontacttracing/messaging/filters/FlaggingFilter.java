package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

/**
* Checker for flag violations.
*/
interface FlaggingFilter {
  /*
  * Returns whether a flag is triggered
  */
  boolean passesFilter(PositiveUser positiveUser, String message);

  /*
  * Returns an error message to be used if the flag is triggered
  */
  String errorMessageToUser();
}