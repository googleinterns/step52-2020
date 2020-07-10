public class LengthFlaggingFilter implements FlaggingFilter{
  private int limitNumOfCharacters = 500;
  public boolean passesFilter(User user, String message) {
    this.user = user;
    if (message.length() > limitNumOfCharacters) {
      return false;
    }
    return true;
  };
  public String errorMessageToUser() {
    return "You've exceed the character limit!\n Please shorten your message and Try again!";
  };
}
