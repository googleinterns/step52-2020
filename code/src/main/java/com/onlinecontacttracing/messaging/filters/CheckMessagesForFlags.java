package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import java.util.List;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;

//checks all the filters
public class CheckMessagesForFlags {
  private final static ArrayList<FlaggingFilter> listOfFilters = new ArrayList<FlaggingFilter> () {{
    add(new HtmlFlaggingFilter());
    add(new LengthFlaggingFilter());
    add(new LinkFlaggingFilter());
    add(new ProfanityFlaggingFilter());
  }};

  private final ArrayList<String> listOfErrorMessages = new ArrayList<String> ();
  public static ArrayList<String> findTriggeredFlags (CheckMessagesForFlags flagChecker, PositiveUser user, String userMessage) {
    CustomizeMessageTriesFlaggingFilter checkNumberOfTries= new CustomizeMessageTriesFlaggingFilter();
    
    //Want to just return if user has exceeded submission limit
    if (!checkNumberOfTries.passesFilter(user, userMessage)) {
      flagChecker.listOfErrorMessages.add(checkNumberOfTries.errorMessageToUser());
      return flagChecker.listOfErrorMessages;
    }
    for (FlaggingFilter filter : listOfFilters) {
      if (!filter.passesFilter(user, userMessage)) {
        flagChecker.listOfErrorMessages.add(filter.errorMessageToUser());
      }
    }
    return flagChecker.listOfErrorMessages;
  }

}