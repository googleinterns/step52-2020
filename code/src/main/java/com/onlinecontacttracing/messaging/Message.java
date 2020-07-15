package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;

public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private String errorMessage;
  private PositiveUser user;

  public Message(SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, PositiveUser user) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.userMessage = customizableMessage.getMessage();
    this.errorMessage = "";
    this.user = user;

  }

  public boolean checkForFlags(CustomizableMessage customizableMessage) {
    String userMessage = customizableMessage.getMessage();
    String userId = customizableMessage.getUserId();
    
    try{
      boolean check = (new NumberOfMessagesFlaggingFilter()).passesFilter(user, userMessage)
      && (new ProfanityFlaggingFilter()).passesFilter(user, userMessage)
      && (new LinkFlaggingFilter()).passesFilter(user, userMessage)
      && (new HtmlFlaggingFilter()).passesFilter(user, userMessage)
      && (new LengthOfMessagesFlaggingFilter()).passesFilter(user, userMessage);
      return check;
    } catch (Exception e) {
      errorMessage = e.toString();
      return false;
    }
  }

  public String compileMessage(String messageLanguage) {
    //need to adjust to change with getting different translations
    String translatedResourceMessage;
    String translatedSystemMessage;
    if (user.userCanMakeMoreDraftsAfterBeingFlagged()) {
      if (checkForFlags(userMessage)) {//default should be english
        if (messageLanguage.equals("EN")) {
        translatedResourceMessage = localityResource.getEnglishTranslation();
        translatedSystemMessage = systemMessage.getEnglishTranslation();
        return translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage);
        }
      } else{
        throw new Exception(errorMessage);
      }
    } else {
      return "";
    }
  }


}