package com.onlinecontacttracing.messaging.filters;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

//Checks if the message contains HTML code
public class HtmlFlaggingFilter implements FlaggingFilter{
  private static ArrayList<String> listOfHtmlIndicators = new ArrayList<String> () {{
        add("<html>");
        add("</html>");
        add("<head>");
        add("</head>");
        add("<title>");
        add("</title>");
        add("body>");
        add("/body");   
    }}; 

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