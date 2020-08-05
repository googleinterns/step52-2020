package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.CheckMessagesForFlags;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Projection;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Compiled message including the opening System Message, user's custom message, and the loacality resources.
*/
public class CompiledMessage {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private String userMessage;
  private PositiveUser user;
  private ArrayList<String> messagesForFrontendDisplay = new ArrayList<String> ();
  //the frontend and backend are not necessarily the same message in case the 
  //user's message triggers flags

  public CompiledMessage(SystemMessage systemMessage, LocalityResource localityResource, String message, PositiveUser positiveUser) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.userMessage = message;
    this.user = positiveUser;
  }

  /**
  * This method checks whether a user's message violates any of the flags and stores these flags.
  */
  public void checkForFlags() {
    CheckMessagesForFlags flagChecker = new CheckMessagesForFlags();
    this.messagesForFrontendDisplay = flagChecker.findTriggeredFlags(this.user, this.userMessage);
  }

  /**
  * This method compiles the messages for frontend and backend user. They will differ if the
  * message has triggered any flags.
  */
  public void compileMessages(String messageLanguage) {
    String translatedResourceMessage;
    String translatedSystemMessage;
    this.user.incrementAttemptedEmailDrafts();
    checkForFlags();
    
    if (messageLanguage.equals("SP")) {
      translatedResourceMessage = localityResource.getEnglishTranslation();
      translatedSystemMessage = systemMessage.getEnglishTranslation();
    }
    else {
      translatedResourceMessage = localityResource.getEnglishTranslation();
      translatedSystemMessage = systemMessage.getEnglishTranslation();
    }
    
    //if any flags are triggered, do not want to include the user's message in the message sent to the backend
    this.messagesForFrontendDisplay.add(0, translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
    if (messagesForFrontendDisplay.size() > 1){
      userMessage = "";
    }
    return translatedSystemMessage.concat("\n").concat(userMessage).concat("\n").concat(translatedResourceMessage);
  }

  /**
  * Returns the compiled message for frontend use.
  */
  public ArrayList<String> getCompiledFrontendDisplayMessage() {
    return this.messagesForFrontendDisplay;
  }

  /**
  * Returns the user's id.
  */  
  public String getUserId() {
    return user.getUserId();
  }
}