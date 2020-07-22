package com.onlinecontacttracing.messaging.filters;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

//Checks if message contains any profanity
public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static ArrayList<String> listOfProfanityIndicators = new ArrayList<String> () {{
    add("fuck");
    add("shit");
    add("bitch");
    add("crap");
    add("damn");
    add("goddamn");
  }};

  public boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfProfanityIndicators = listOfProfanityIndicators.size();
      String profanityIndicator;

      message = prepMessageForCheck(message);

      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicator = listOfProfanityIndicators.get(profanityIndicatorIndex);
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