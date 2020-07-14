package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;

public class NumberOfMessagesFlaggingFilter implements FlaggingFilter throws Exception{
  private static int limitNumOfMessages = 100;

  public static boolean passesFilter(PositiveUser positiveUser, String message){
    if (positiveUser.getSentNumberOfMessages > limitNumOfMessages) {
      throw Exception(errorMessageToUser());
    }
    return true;
  }
  public static String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send.";
  }
}
