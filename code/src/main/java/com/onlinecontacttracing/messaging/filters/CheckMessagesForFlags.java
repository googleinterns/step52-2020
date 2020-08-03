package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import java.util.List;

/**
* Checks whether a message violates any of the flags.
*/
public class CheckMessagesForFlags {
  private final static ArrayList<FlaggingFilter> listOfFilters = new ArrayList<FlaggingFilter> () {{
    add(new HtmlFlaggingFilter());
    add(new LengthFlaggingFilter());
    add(new LinkFlaggingFilter());
    add(new ProfanityFlaggingFilter());
  }};

  private final ArrayList<String> listOfErrorMessages = new ArrayList<String> ();

  /**
  * Returns a list of messages from any violated flags.
  */
  public ArrayList<String> findTriggeredFlags (PositiveUser user, String userMessage) {
    CustomizeMessageTriesFlaggingFilter checkNumberOfTries= new CustomizeMessageTriesFlaggingFilter();
    
    //Want to just return if user has exceeded submission limit
    if (!checkNumberOfTries.passesFilter(user, userMessage)) {
      this.listOfErrorMessages.add(checkNumberOfTries.errorMessageToUser());
      return this.listOfErrorMessages;
    }
    for (FlaggingFilter filter : listOfFilters) {
      if (!filter.passesFilter(user, userMessage)) {
        this.listOfErrorMessages.add(filter.errorMessageToUser());
      }
    }
    return this.listOfErrorMessages;
  }
}