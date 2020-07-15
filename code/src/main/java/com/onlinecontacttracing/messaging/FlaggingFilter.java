/**
  I really like the way this describes different flagging mechanisms. It might be worth giving the interface for the FlaggingFilter 
  (which should probably have two methods passesFilter(User user, String message) => boolean, and errorMessageToUser() => String ) 
  and then naming the classes you will implement that implement FlaggingFilter (like "LengthFlaggingFilter", "NumberOfMessagesFlaggingFilter", 
  "ProfanityFlaggingFilter", etc.
*/

package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;

public interface FlaggingFilter {
  public static boolean passesFilter(PositiveUser positiveUser, String message);
  public static String errorMessageToUser();
}