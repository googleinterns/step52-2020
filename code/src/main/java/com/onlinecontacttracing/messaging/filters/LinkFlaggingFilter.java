package com.onlinecontacttracing.messaging.filters;

import java.util.ArrayList;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;

public class LinkFlaggingFilter implements FlaggingFilter{
  private static ArrayList<String> listOfLinkIndicators = new ArrayList<String> () {{
        add("https://");
        add("http://");
        add(".com");
        add(".org");
        add(".gov");
        add(".net");
        add(".co");
        add(".us");
        add("www.");
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
