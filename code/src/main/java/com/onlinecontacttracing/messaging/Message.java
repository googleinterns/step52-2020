package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.ChecksMessagesForFlags;

public class Message {
  private SystemMessage systemMessage;
  private LocalityResource localityResource;
  private CustomizableMessage customizableMessage;
  private String userMessage;
  private String errorMessage;
  private PositiveUser user;
  private List<String> listOfFlagMessages

  public Message(SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, PositiveUser user) {
    this.systemMessage = systemMessage;
    this.localityResource = localityResource;
    this.customizableMessage =  customizableMessage;
    this.userMessage = customizableMessage.getMessage();
    this.errorMessage = "";
    this.user = user;

  }

  public boolean checkForFlags() {
    CheckMessagesForFlags flagChecker = new CheckMessagesForFlags();
    listOfFlagMessages = CheckMessagesForFlags.findTriggeredFlags(flagChecker, user, userMessage);
  }

  public String compileMessage(String messageLanguage) {
    //need to adjust to change with getting different translations
    String translatedResourceMessage;
    String translatedSystemMessage;
    if (user.userCanMakeMoreDraftsAfterBeingFlagged()) {
      if (listOfFlagMessages.size() == 0) {//default should be english
        if (messageLanguage.equals("SP")) {
        translatedResourceMessage = localityResource.getEnglishTranslation();
        translatedSystemMessage = systemMessage.getEnglishTranslation();
        return translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage);
        }
        else {
          translatedResourceMessage = localityResource.getEnglishTranslation();
          translatedSystemMessage = systemMessage.getEnglishTranslation();
          return translatedSystemMessage.concat(userMessage).concat(translatedResourceMessage);
        }
      }
      return "";//should return error messages
    }
    return "";
  }

}