package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FileReader;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

//Checks if message contains any profanity
//assumes all profanity in language is not included in longer words, e.g. "ass" will trigger the flag, "bass" will not
public class ProfanityFlaggingFilter implements FlaggingFilter{
  private static final String[]  LIST_OF_PROFANITY_INDICATORS =  FileReader.getListFromFile("profanity-indicators.txt");;// = FileReader.getListFromFile(new File("profanity-indicators.txt"));
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    int numOfProfanityIndicators = LIST_OF_PROFANITY_INDICATORS.length;
    String profanityIndicatorWord;
    int messageLength = message.length();
    boolean isSelfContainedWord;   
    message = prepMessageForCheck(message);

    for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
      profanityIndicatorWord = LIST_OF_PROFANITY_INDICATORS[profanityIndicatorIndex];
        //check existence of profane word
        if (message.indexOf(profanityIndicatorWord) > -1) {//profane word exists
          isSelfContainedWord = checkIfWordIsSelfContained(message, profanityIndicatorWord, messageLength);
          if (isSelfContainedWord) {
            //profane word exists and is its own word => does not pass filter
            return false;
          }
        }
      }
      return true;
  }

  private boolean checkIfWordIsSelfContained (String message, String word, int messageLength) {
    int startOfWordIndex = message.indexOf(word);
    int endOfWordIndex = startOfWordIndex + word.length()-1;
    String characterBeforeWord;
    String characterAfterWord;
    //check is self-contained/not within another, larger, word
    //i.e. only spaces/punctuation on either end of the word
    if (startOfWordIndex > 0) {
      characterBeforeWord = message.substring(startOfWordIndex-1, startOfWordIndex);
      if (!characterBeforeWord.equals(" ") && !checkIfCharIsPunctuation(characterBeforeWord)) {
        return false;
      }
    }
    if (endOfWordIndex < messageLength-1) {
      characterAfterWord = message.substring(endOfWordIndex+1, endOfWordIndex+2);
      if (!characterAfterWord.equals(" ") && !checkIfCharIsPunctuation(characterAfterWord)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkIfCharIsPunctuation(String character) {
    String[] listOfPunctuation = FileReader.getListFromFile("punctuation-list.txt");
    int numberOfPunctuation = listOfPunctuation.length;
    for (int index = 0; index < numberOfPunctuation; index++) {
      if (listOfPunctuation[index].equals(character)) {
        return true;
      }
    }
    return false;
  }

  private String prepMessageForCheck(String message) {
    message = replaceSymbolsWithLetters(message);
    message = message.toLowerCase();
    return message;
  }

  private String replaceSymbolsWithLetters(String message) {
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
    return message;
  }
  
  public String errorMessageToUser() {
    return "We believe that your message contains profanity. Please remove it and try again.";
  };
}