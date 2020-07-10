public class NumberOfMessagesFlaggingFilter implements FlaggingFilter{
  int limitNumOfMessages = 100;

  public boolean passesFilter(User user, String message){
    return user.numberOfMessagesSent > limitNumOfMessages;
  }
  public String errorMessageToUser() {
    return "You have exceeded the max. number of messages you can send."
  }
}
