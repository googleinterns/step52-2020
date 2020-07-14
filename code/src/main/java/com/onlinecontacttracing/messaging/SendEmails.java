package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.Message;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.messaging.MessagingSetup;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

public class SendEmails {

  //get contacts list from objectify
  //create message
  //create instance of messaging setup and try to email
  //store results in database
  private String emailHeader;
  private String emailBody;
  private Message messageObject;
  private ArrayList<PotentialContact> contactsList;
  
  public SendEmails (SystemMessage systemMessage, String localityResource, CustomizableMessage customizableMessage, String emailHeader, String messageLanguage, ArrayList<PotentialContact> contactsList) {
    messageObject = new Message(systemMessage, localityResource, customizableMessage);
    emailBody = messageObject.compileMessage(messageLanguage);
    this.emailHeader = emailHeader;
    this.contactsList = contactsList;
  }

  public void sendEmailsOut () {
    PotentialContact contact;
    for(PotentialContact contact : contactsList) {
      
    }
  }

}