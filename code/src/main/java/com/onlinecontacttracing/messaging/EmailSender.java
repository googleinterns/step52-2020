package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.CompileMessageServlet;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.messaging.MessagingSetup;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
// import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.mail.internet.MimeMessage;
import com.google.cloud.auth.samples.GetServiceAccountCredentials;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.onlinecontacttracing.messaging.MessagingSetup;
import javax.mail.MessagingException;

public class EmailSender {

  private String emailSubject;
  private ArrayList<PotentialContact> contactsList;
  private Gmail service;
  private PositiveUser user; //consider removing
  private CompiledMessage compiledMessage;
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static final String APPLICATION_NAME = "Online Contact Tracing";
  
  public EmailSender(String emailSubject, ArrayList<PotentialContact> contactsList, CompiledMessage compiledMessage) {
    try{
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      this.emailSubject= emailSubject;
      this.contactsList = contactsList;
      this.compiledMessage = compiledMessage;
      GoogleCredential serviceAccountCredential = GetServiceAccountCredentials.getServiceAccountCredentials();
      service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, serviceAccountCredential)
                  .setApplicationName(APPLICATION_NAME)
                  .build();
      this.user = user;
    } catch (GeneralSecurityException e) {
      //do something
    } catch (Exception e) {
      //do something
    }
  }


  public void sendEmailsOut(String messageLanguage) {
    this.compiledMessage.compileMessages(messageLanguage);
    String emailBody = compiledMessage.getCompiledBackendMessage();
    PotentialContact contact;
    MimeMessage email;
    for(PotentialContact contactName : this.contactsList) {
      try{ 
        email = MessagingSetup.createEmail(contactName.getEmail(), user.getUserEmail()/*Switch email*/, this.emailSubject, emailBody);
        MessagingSetup.sendMessage(service, user.getUserId(), email);//fix
      } catch (MessagingException e) {
        //do something
      } catch (Exception e) {
        //do something
      }
    }
  }

}