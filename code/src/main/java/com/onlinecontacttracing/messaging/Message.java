package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;

public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;

  public Message(SystemMessage systemMessage, String localityResource, CustomizableMessage customizableMessage) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.userMessage = customizableMessage.getMessage();
  }

  public boolean checkForFlags(CustomizableMessage customizableMessage) {
    String userMessage = customizableMessage.getMessage();
    String userId = customizableMessage.getUserId();
    
    //to get error messages, store in hashmap in order with an associated number
    //store in alphabetical order
    try{
      NumberOfMessagesFlaggingFilter.passesFilter(userId, userMessage)
      && ProfanityFlaggingFilter.passesFilter(userId, userMessage)
      && LinkFlaggingFilter.passesFilter(userId, userMessage)
      && HtmlOfMessagesFlaggingFilter.passesFilter(userId, userMessage)
      && LengthFlaggingFilter.passesFilter(userId, userMessage);
      return true;
    } catch (Exception e) {
      
      //can get exception message with toString();
    }
  }

  public String compileMessage(String messageLanguage) {
    //need to adjust to change with getting different translations
    String translatedResourceMessage;
    String translatedSystemMessage;
    if (messageLanguage.equals("EN")) {
      translatedMessage = localityResource.getEnglishTranslation();
      translatedSystemMessage = systemMessage.getEnglishTranslation();
      return translatedSystemMessage.concat(userMessage).concat(translatedMessage);
    }
    return "";
  }


}