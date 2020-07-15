package com.onlinecontacttracing.messaging;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static ArrayList<String> listOfProfanityIndicators = new ArrayList<String> () {{
        add("badword");
    }};

  public boolean passesFilter(PositiveUser positiveUser, String message) throws Exception {
      int numOfProfanityIndicators = listOfProfanityIndicators.size();
      String profanityIndicator;
      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicator = listOfProfanityIndicators.get(profanityIndicatorIndex);
        if (message.indexOf(profanityIndicator) > -1) {
          throw new Exception(new ProfanityFlaggingFilter().errorMessageToUser());
        }
      }
      return true;
    }
  public String errorMessageToUser() {
    return "We believe that your message contains profanity. Please remove it and try again.";
  };
}