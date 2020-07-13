package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;

public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;

  public Message(SystemMessage systemMessage, String LocalityResource, CustomizableMessage customizableMessage) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage = customizableMessage;
  }

  public boolean checkForFlags(CustomizableMessage customizableMessage) {
    String userMessage = customizableMessage.getMessage();
    String userId = customizableMessage.getUserId();
    
    //to get error messages, store in hashmap in order with an associated number
    //store in alphabetical order
    if(!NumberOfMessagesFlaggingFilter.passesFilter(userId, userMessage)) {
      throw new Exception (NumberOfMessagesFlaggingFilter.errorMessageToUser);
    }
    if(!ProfanityFlaggingFilter.passesFilter(userId, userMessage)) {
      throw new Exception (NumberOfMessagesFlaggingFilter.errorMessageToUser);
    }
    if(!LinkFlaggingFilter.passesFilter(userId, userMessage)) {
      throw new Exception (NumberOfMessagesFlaggingFilter.errorMessageToUser);
    }
    if(!HtmlOfMessagesFlaggingFilter.passesFilter(userId, userMessage)) {
      throw new Exception (NumberOfMessagesFlaggingFilter.errorMessageToUser);
    }
    if(!LengthFlaggingFilter.passesFilter(userId, userMessage)) {
      throw new Exception (NumberOfMessagesFlaggingFilter.errorMessageToUser);
    }
    return true;
  }

  public String compileMessage(String messageLanguage) {
    //need to adjust to change with getting different translations
    if (messageLanguage.equals("EN")) {
      return systemMessage.getEnglishTranslation().concat(customizableMessage.getMessage()).concat(localityResource.getEnglishTranslation());
    }
    return "";
  }


}