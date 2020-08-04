package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.CheckMessagesForFlags;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Projection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class CompiledMessage {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private PositiveUser user;
  //for backend use
  private String messagesForBackendUse;
  //for frontend display
  private ArrayList<String> messagesForFrontendDisplay = new ArrayList<String> ();
  //the frontend and backend are not necessarily the same message in case the 
  //user's message triggered flags

  public CompiledMessage(SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, PositiveUser positiveUser) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage = customizableMessage;
    this.userMessage = this.customizableMessage.getMessage();
    this.user = positiveUser;
  }

  public void checkForFlags() {
    CheckMessagesForFlags flagChecker = new CheckMessagesForFlags();
    this.messagesForFrontendDisplay = flagChecker.findTriggeredFlags(this.user, this.userMessage);
  }

  //if flags are triggered, returns message and list of errors, else returns message
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
    this.messagesForBackendUse = (translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
  }

  public String getCompiledBackendMessage() {
    //returns message with no userMessage if there are errors, else return message with userMessage
    return this.messagesForBackendUse;
    
  }

  public ArrayList<String> getCompiledFrontendDisplayMessage() {
    return this.messagesForFrontendDisplay;
  }
}