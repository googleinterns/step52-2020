package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.FileReader;
import java.lang.Exception;
import java.util.ArrayList;

//Checks if message contains any profanity
//assumes all profanity in language is not included in longer words, e.g. "ass" will trigger the flag, "bass" will not
public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static final ArrayList<String> LIST_OF_PROFANITY_INDICATORS = FileReader.getListFromFile("profanity-indicators.txt");
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfProfanityIndicators = this.LIST_OF_PROFANITY_INDICATORS.size();
      String profanityIndicator;

      message = prepMessageForCheck(message);
      int startOfProfaneWord;
      int endOfProfaneWord;
      boolean isSeparateWord = true;
      int messageLength = message.length();
      char characterBeforeWord;
      char characterAfterWord;

      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicatorWord = this.LIST_OF_PROFANITY_INDICATORS.get(profanityIndicatorIndex);
        startOfProfaneWord = message.indexOf(profanityIndicatorWord);
        endOfProfaneWord = startOfProfaneWord + profanityIndicatorWord.length()-1;
        //check existence of profane word
        if (startOfProfaneWord > -1) {
          //check profane word is its own word
          //i.e. only spaces/punctuation on either end of the word
          if (startOfProfaneWord > 0) {
            characterBeforeWord = message.charAt(startOfProfaneWord-1);
            if (characterBeforeWord != ' ' || !checkIfCharIsPunctuation(characterBeforeWord)) {
              isSeparateWord = false;
            }
          }
          if (endOfProfaneWord < messageLength) {
            characterAfterWord = message.charAt(endOfProfaneWord+1);
            if ( characterAfterWord != ' ' || !checkIfCharIsPunctuation(characterAfterWord)){
              isSeparateWord = false;
            }
          }
          if (isSeparateWord) {
            //profane word exists and is its own word, does not pass filter
            return false;
          }
        }
      }
      return true;
    }

  boolean checkIfCharIsPunctuation(Character character) {
    ArrayList<Character> listOfPunctuation  = FileReader.getListFromFile("profanity-indicators.txt");
    return listOfPunctuation.contains(character);
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