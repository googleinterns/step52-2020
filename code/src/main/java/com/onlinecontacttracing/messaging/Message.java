package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;

public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private String errorMessage;

  public Message(SystemMessage systemMessage, String localityResource, CustomizableMessage customizableMessage) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.userMessage = customizableMessage.getMessage();
    this.errorMessage = "";
  }

  public boolean checkForFlags(CustomizableMessage customizableMessage) {
    String userMessage = customizableMessage.getMessage();
    String userId = customizableMessage.getUserId();
    
    //to get error messages, store in hashmap in order with an associated number
    //store in alphabetical order
    try{
      boolean check = NumberOfMessagesFlaggingFilter.passesFilter(userId, userMessage)
      && ProfanityFlaggingFilter.passesFilter(userId, userMessage)
      && LinkFlaggingFilter.passesFilter(userId, userMessage)
      && HtmlOfMessagesFlaggingFilter.passesFilter(userId, userMessage)
      && LengthFlaggingFilter.passesFilter(userId, userMessage);
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

    if (checkForFlags) {//default should be english
      if (messageLanguage.equals("EN")) {
      translatedMessage = localityResource.getEnglishTranslation();
      translatedSystemMessage = systemMessage.getEnglishTranslation();
      return translatedSystemMessage.concat(userMessage).concat(translatedMessage);
      } else {
        return "";
      }
    }
    else{
      throw new Exception(errorMessage);
    }
  }


}