package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  private static final int LIMIT_NUMBER_OF_MESSAGES = 100;

  public boolean passesFilter(PositiveUser positiveUser, String message) {
    if (positiveUser.getNumberOfEmailsSent() > LIMIT_NUMBER_OF_MESSAGES) {
      return false;
    }
    return true;
  }
  public String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send.";
  }
}
