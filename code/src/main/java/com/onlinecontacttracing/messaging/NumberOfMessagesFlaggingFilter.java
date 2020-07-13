package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;

public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  private static int limitNumOfMessages = 100;

  public static boolean passesFilter(PositiveUser positiveUser, String message){
    return positiveUser.getSentNumberOfMessages > limitNumOfMessages;
  }
  public static String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send.";
  }
}
