public class LinkFlaggingFilter implements FlaggingFilter{
  private HashMap<String, Integer> listOfLinkIndicatorsHM = new HashMap<>() {{
        put("https://", 6);
        put("http://", 6);
        put(".com", 6);
        put(".org", 6);
        put(".gov", 6);
        put(".net", 6);
        put(".co", 6);
        put(".us", 6);
        put("www.", 6);
        put("@", 1);
        put(":", 2);
        put("?", 1);
        put("#", 3);
    }};; //e.g. ".com", ".org", "www."
    private ArrayList<String> listOfLinkIndicators = new ArrayList<>() {{
        add("https://");
        add("http://");
        add(".com");
        add(".org");
        add(".gov");
        add(".net");
        add(".co");
        add(".us");
        add("www.");
        add("@");
        add(":");
        add("?");
        add("#");
    }};
  private int flagThreshold = 10;
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    int numOfLinkIndicators = listOfLinkIndicators.size();
    String linkIndicator;
    for (int linkIndicatorIndex = 0; linkIndicatorIndex < numOfLinkIndicators; linkIndicatorIndex++) {
      linkIndicator = listOfLinkIndicators.get(linkIndicatorIndex);
      if (message.indexOf(linkIndicator) > -1) {
        return false;
      }
    }
    return true;
  }

  public String errorMessageToUser() {
    return "We think there might be a URL or link in your message. Please remove them and try again!";
  }

  

}
