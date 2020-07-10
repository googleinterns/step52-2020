public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  int limitNumOfMessages;

  public boolean passesFilter(User user, String message);
  public String errorMessageToUser();
}
