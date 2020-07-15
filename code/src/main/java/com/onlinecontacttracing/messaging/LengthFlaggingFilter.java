package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

public class LengthFlaggingFilter implements FlaggingFilter{
  private static int limitNumOfCharacters = 500;
  

  public boolean passesFilter(PositiveUser positiveUser, String message) throws Exception {
    if (message.length() > limitNumOfCharacters) {
      throw new Exception(new LengthFlaggingFilter().errorMessageToUser());
    }
    return true;
  };
  public String errorMessageToUser() {
    return "You've exceed the character limit!\n Please shorten your message and Try again!";
  };
}
