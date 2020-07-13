package com.onlinecontacttracing.messaging;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.FlaggingFilter;

public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static ArrayList<String> listOfProfanity = new ArrayList<String> () {{
        
    }};

  public static boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfProfanityIndicators = listOfProfanityIndicators.size();
      String profanityIndicator;
      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicator = listOfProfanityIndicators.get(profanityIndicatorIndex);
        if (message.indexOf(profanityIndicator) > -1) {
          return false;
        }
      }
      return true;
    }
  public static String errorMessageToUser() {
    return "We believe that your message contains profanity. Please remove it and try again.";
  };
}