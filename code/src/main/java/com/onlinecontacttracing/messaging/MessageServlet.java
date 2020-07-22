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

@WebServlet("/messaging")
public class MessageServlet extends HttpServlet {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private PositiveUser user;
  //for backend use
  private String compiledMessage;
  //for frontend display
  private ArrayList<String> statusMessage = new ArrayList<String> ();


  public MessageServlet(SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, PositiveUser user) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.user = user;

  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    String messageLanguage = request.getParameter("language");
    response.getWriter().println(statusListToShowUser(messageLanguage));
  }

  public void setUserMessage() {
    this.userMessage = this.customizableMessage.getMessage();
  }

  public void checkForFlags() {
    CheckMessagesForFlags flagChecker = new CheckMessagesForFlags();
    statusMessage = CheckMessagesForFlags.findTriggeredFlags(flagChecker, user, userMessage);
  }

  //if flags are triggered, returns message and list of errors, else returns message
  public ArrayList<String> statusListToShowUser(String messageLanguage) {
    String translatedResourceMessage;
    String translatedSystemMessage;
    user.incrementAttemptedEmailDrafts();
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
    statusMessage.add(translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
    if (statusMessage.size() > 0){
      userMessage = "";
    }
    compiledMessage = (translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage));
    return statusMessage;
  
  }

  public String compileMessage (ArrayList<String> statusMessage) {
    //returns message with no userMessage if there are errors, else return message with userMessage
    if (statusMessage.size() > 1) {
      return compiledMessage;
    }
    return statusMessage.get(0);
  }
}