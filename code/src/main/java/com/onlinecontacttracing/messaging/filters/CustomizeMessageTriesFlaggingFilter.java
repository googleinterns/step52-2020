package com.onlinecontacttracing.messaging.filters;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

public class CustomizeMessageTriesFlaggingFilter implements FlaggingFilter{
  private int maxNumberOfTries = 3; 

    public boolean passesFilter(PositiveUser positiveUser, String message) {
      int userNumberOfTries = positiveUser.getAttemptedEmailDrafts();
      return maxNumberOfTries > userNumberOfTries;
    }
    
    public String errorMessageToUser() {
      return "You've exceeded the number of tries to customize your message.";
    }
}