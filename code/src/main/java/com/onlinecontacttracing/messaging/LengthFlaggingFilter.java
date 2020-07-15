package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;

public class LengthFlaggingFilter implements FlaggingFilter{
  private static int limitNumOfCharacters = 500;
  

  public static boolean passesFilter(PositiveUser positiveUser, String message) throws Exception {
    if (message.length() > limitNumOfCharacters) {
      throw Exception(errorMessageToUser());
    }
    return true;
  };
  public static String errorMessageToUser() {
    return "You've exceed the character limit!\n Please shorten your message and Try again!";
  };
}
