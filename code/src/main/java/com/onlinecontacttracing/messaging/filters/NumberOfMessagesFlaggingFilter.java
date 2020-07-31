package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

<<<<<<< HEAD
public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  private static final int LIMIT_NUMBER_OF_MESSAGES = 100;

  public boolean passesFilter(PositiveUser positiveUser, String message) {
    return positiveUser.getNumberOfEmailsSent() <= this.LIMIT_NUMBER_OF_MESSAGES;
  }
=======
/*
* Checker for number of sent messages
*/
public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  private static final int LIMIT_NUMBER_OF_MESSAGES = 100;

  /*
  * Returns whether or not a user has exceeded the limit on sent messages
  */
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    return positiveUser.getNumberOfEmailsSent() <= this.LIMIT_NUMBER_OF_MESSAGES;
  }
  
  /*
  * Returns an error message to be user if user has exceeded the limit on sent messages
  */
>>>>>>> master
  public String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send.";
  }
}
