package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.FileReader;
import java.lang.Exception;
import java.util.ArrayList;

//Checks if message contains any profanity
public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static final ArrayList<String> LIST_OF_PROFANITY_INDICATORS = FileReader.getListFromFile("profanity-indicators.txt");
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfProfanityIndicators = this.LIST_OF_PROFANITY_INDICATORS.size();
      String profanityIndicator;

      message = prepMessageForCheck(message);

      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicator = this.LIST_OF_PROFANITY_INDICATORS.get(profanityIndicatorIndex);
        if (message.indexOf(profanityIndicator) > -1) {
          return false;
        }
      }
      return true;
    }

  String prepMessageForCheck(String message) {
    message = replaceSymbolsWithLetters(message);
    message = message.toLowerCase();
    return message;
  }

  String replaceSymbolsWithLetters(String message) {
    message = message.replaceAll("0","o");
    message = message.replaceAll("1","i");
    message = message.replaceAll("3","e");
    message = message.replaceAll("4","a");
    message = message.replaceAll("5","s");
    message = message.replaceAll("6","g");
    message = message.replaceAll("7","t");
    message = message.replaceAll("8","b");
    message = message.replaceAll("9","g");
    message = message.replaceAll("@","a");
    message = message.replaceAll("!","i");
    message = message.replaceAll("!","i");
    return message;
  }
  
  public String errorMessageToUser() {
    return "We believe that your message contains profanity. Please remove it and try again.";
  };
}