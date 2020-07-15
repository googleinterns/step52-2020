package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;

public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  private static int limitNumOfMessages = 100;

  public static boolean passesFilter(PositiveUser positiveUser, String message) throws Exception {
    if (positiveUser.getSentNumberOfMessages > limitNumOfMessages) {
      throw Exception(errorMessageToUser());
    }
    return true;
  }
  public static String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send.";
  }
}
