public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  int limitNumOfMessages = 100;

  public boolean passesFilter(PositiveUser positiveUser, String message){
    return positiveUser.getSentNumberOfMessages > limitNumOfMessages;
  }
  public String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send."
  }
}
