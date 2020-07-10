public class ProfanityFlaggingFilter implements FlaggingFilter{
  ArrayList<String> listOfProfanity = new ArrayList<>() {{
        add("fuck");
        add("shit");
        add("bitch");
        add("crap");
        add("damn");
        add("ass");
        add("bastard");
    }};

  public boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfProfanityIndicators = listOfProfanityIndicators.size();
      String profanityIndicator;
      for (int profanityIndicatorIndex = 0; profanityIndicatorIndex < numOfProfanityIndicators; profanityIndicatorIndex++) {
        profanityIndicator = listOfProfanityIndicators.get(profanityIndicatorIndex);
        if (message.indexOf(profanityIndicator) > -1) {
          return false;
        }
      }
      return true;
    }
  public String errorMessageToUser() {
    return "We believe that your message contains profanity. Please remove it and try again.";
  };
}