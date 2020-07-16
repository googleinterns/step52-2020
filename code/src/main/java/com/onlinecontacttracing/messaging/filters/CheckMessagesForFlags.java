package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import java.util.List;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;

public class CheckMessagesForFlags {
  private final static List<FlaggingFilter> listOfFilters = new ArrayList<FlaggingFilter> () {{
    add(new HtmlFlaggingFilter());
    add(new LengthFlaggingFilter());
    add(new LinkFlaggingFilter());
    add(new ProfanityFlaggingFilter());
  }};

  private final List<String> listOfErrorMessages = new ArrayList<String> ();
  public static List<String> findTriggeredFlags (CheckMessagesForFlags flagChecker, PositiveUser user, String userMessage) {
    CustomizeMessageTriesFlaggingFilter checkNumberOfTries= new CustomizeMessageTriesFlaggingFilter();
    if (checkNumberOfTries.passesFilter(user, userMessage)) {
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