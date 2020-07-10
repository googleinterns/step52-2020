public class HtmlFlaggingFilter implements FlaggingFilter{
  ArrayList<String> listOfHtmlIndicators = new ArrayList<>() {{
        add("<html>");
        add("</html>");
        add("<head>");
        add("</head>");
        add("<title>");
        add("</title>");
        add("body>");
        add("/body");   
    }}; //e.g. "<html>"

    public boolean passesFilter(PositiveUser positiveUser, String message) {
      int numOfHtmlIndicators = listOfHtmlIndicators.size();
      String htmlIndicator;
      for (int htmlIndicatorIndex = 0; htmlIndicatorIndex < numOfHtmlIndicators; htmlIndicatorIndex++) {
        htmlIndicator = listOfHtmlIndicators.get(htmlIndicatorIndex);
        if (message.indexOf(htmlIndicator) > -1) {
          return false;
        }
      }
      return true;
    }
    public String errorMessageToUser() {
      return "We think there might be some HTML code in your message. Please remove it and try again!";
    }
}