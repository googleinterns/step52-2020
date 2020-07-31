package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

<<<<<<< HEAD
//Checks that the length of the message is within limits
public class LengthFlaggingFilter implements FlaggingFilter{
  private static final int LIMIT_NUMBER_OF_CHARACTERS = 500;
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    return message.length() <= this.LIMIT_NUMBER_OF_CHARACTERS;
  };
=======
/*
* Checker for message length.
*/
public class LengthFlaggingFilter implements FlaggingFilter {
  private static final int LIMIT_NUMBER_OF_CHARACTERS = 500;
  
  /*
  * Returns an error message if the message exceeds the max. length.
  */
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    return message.length() <= this.LIMIT_NUMBER_OF_CHARACTERS;
  };
  
  /*
  * Returns an error message to be used if the message is too long.
  */
>>>>>>> master
  public String errorMessageToUser() {
    return "You've exceed the character limit!\n Please shorten your message and Try again!";
  };
}
