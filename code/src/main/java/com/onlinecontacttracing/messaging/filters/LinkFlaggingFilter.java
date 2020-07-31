package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.messaging.filters.FileReader;
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import java.lang.Exception;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//Checks if message contains any links/URLs
public class LinkFlaggingFilter implements FlaggingFilter{
  private static final String[] LIST_OF_LINK_INDICATORS = FileReader.getListFromFile("link-indicators.txt");
  
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    int numOfLinkIndicators = this.LIST_OF_LINK_INDICATORS.length;
    String linkIndicator;
    for (int linkIndicatorIndex = 0; linkIndicatorIndex < numOfLinkIndicators; linkIndicatorIndex++) {
      linkIndicator = this.LIST_OF_LINK_INDICATORS[linkIndicatorIndex];
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
