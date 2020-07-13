package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;

public class LengthFlaggingFilter implements FlaggingFilter{
  private static int limitNumOfCharacters = 500;
  

  public static boolean passesFilter(PositiveUser positiveUser, String message) {
    if (message.length() > limitNumOfCharacters) {
      return false;
    }
    return true;
  };
  public static String errorMessageToUser() {
    return "You've exceed the character limit!\n Please shorten your message and Try again!";
  };
}
