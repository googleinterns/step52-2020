/**
  I really like the way this describes different flagging mechanisms. It might be worth giving the interface for the FlaggingFilter 
  (which should probably have two methods passesFilter(User user, String message) => boolean, and errorMessageToUser() => String ) 
  and then naming the classes you will implement that implement FlaggingFilter (like "LengthFlaggingFilter", "NumberOfMessagesFlaggingFilter", 
  "ProfanityFlaggingFilter", etc.
*/

public interface FlaggingFilter {
  public boolean passesFilter(String userId, String message);
  public String errorMessageToUser();
}