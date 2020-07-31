package com.onlinecontacttracing.messaging.filters;

<<<<<<< HEAD
import com.onlinecontacttracing.messaging.filters.FlaggingFilter;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.FileReader;
import java.lang.Exception;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


//Checks if the message contains HTML code
public class HtmlFlaggingFilter implements FlaggingFilter{
  private static final String[] LIST_OF_HTML_INDICATORS = FileReader.getListFromFile("html-indicators.txt");

    public boolean passesFilter(PositiveUser positiveUser, String message) {
      
        int numOfHtmlIndicators = this.LIST_OF_HTML_INDICATORS.length;
        String htmlIndicator;
        for (int htmlIndicatorIndex = 0; htmlIndicatorIndex < numOfHtmlIndicators; htmlIndicatorIndex++) {
          htmlIndicator = this.LIST_OF_HTML_INDICATORS[htmlIndicatorIndex];
          if (message.indexOf(htmlIndicator) > -1) {
            return false;
          }
        }
        return true;
      
    }
    
    public String errorMessageToUser() {
      return "We think there might be some HTML code in your message. Please remove it and try again!";
    }
=======
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

/*
* Checker for HTML code.
*/
public class HtmlFlaggingFilter implements FlaggingFilter {
  private static final String[] LIST_OF_HTML_INDICATORS = FileReader.getListFromFile("html-indicators.txt");

  /*
  * Returns whether or not a message contains HTML code.
  */
  public boolean passesFilter(PositiveUser positiveUser, String message) {
    int numOfHtmlIndicators = this.LIST_OF_HTML_INDICATORS.length;
    String htmlIndicator;
    for (int htmlIndicatorIndex = 0; htmlIndicatorIndex < numOfHtmlIndicators; htmlIndicatorIndex++) {
      htmlIndicator = this.LIST_OF_HTML_INDICATORS[htmlIndicatorIndex];
      if (message.indexOf(htmlIndicator) > -1) {
        return false;
      }
    }
    return true;
  }

  /*
  * Returns an error message to be used if the message contains HTML code.
  */
  public String errorMessageToUser() {
    return "We think there might be some HTML code in your message. Please remove it and try again!";
  }
>>>>>>> master
}