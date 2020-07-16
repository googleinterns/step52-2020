package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import java.util.List;
import com.onlinecontacttracing.messaging.FlaggingFilter;

public class ChecksMessagesForFlags {
  private final static List<FlaggingFilter> listOfFilters = new ArrayList<FlaggingFilter> () {{
    add(new NumberOfMessagesFlaggingFilter());
    add(new HtmlFlaggingFilter());
    add(new LengthFlaggingFilter());
    add(new LinkFlaggingFilter());
    add(new ProfanityFlaggingFilter());
  }};

  private final List<String> listOfErrorMessages = new ArrayList<String> ();
  public static String findTriggeredFlags (ChecksMessagesForFlags flagChecker, PositiveUser user, String userMessage) {
    for (FlaggingFilter filter : listOfFilters) {
      if (!filter.passesFilter(user, userMessage)) {
        flagChecker.listOfErrorMessages.add(filter.errorMessageToUser());
      }
    }
    return true;
  }

}