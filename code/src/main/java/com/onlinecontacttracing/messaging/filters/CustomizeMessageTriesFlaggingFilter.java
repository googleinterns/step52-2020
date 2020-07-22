package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;
import java.util.ArrayList;

//Checks if user has attempted to submit a message too many times
public class CustomizeMessageTriesFlaggingFilter implements FlaggingFilter{
  private static final int MAX_NUMBER_OF_TRIES = 3; 

    public boolean passesFilter(PositiveUser positiveUser, String message) {
      int userNumberOfTries = positiveUser.getAttemptedEmailDrafts();
      return MAX_NUMBER_OF_TRIES > userNumberOfTries;
    }
    
    public String errorMessageToUser() {
      return "You've exceeded the number of tries to customize your message.";
    }
}