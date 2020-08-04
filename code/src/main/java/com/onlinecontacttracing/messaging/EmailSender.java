package com.onlinecontacttracing.messaging;

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
import java.util.Set;
import javax.mail.internet.MimeMessage;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.onlinecontacttracing.messaging.MessagingSetup;
import javax.mail.MessagingException;
import java.util.logging.Logger;
import com.onlinecontacttracing.storage.PersonEmail;
import java.util.ArrayList;

public class EmailSender {

  private static final String serviceAccountId = "online-contact-tracing@appspot.gserviceaccount.com";
  private static final String emailToSendWith = "cccoders@onlinecontacttracing.com";
   private static final String CREDENTIALS_FILE_PATH = "online-contact-tracing-f798898872f4.p12";
  private String emailSubject;
  private ArrayList<PersonEmail> contactsList;
  private Gmail service;
  private CompiledMessage compiledMessage;
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static final String APPLICATION_NAME = "Online Contact Tracing";
  static final Logger log = Logger.getLogger(EmailSender.class.getName());
  

  public EmailSender(String emailSubject, ArrayList<PersonEmail> contactsList, CompiledMessage compiledMessage) {

    try{
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      this.emailSubject= emailSubject;
      this.contactsList = contactsList;
      this.compiledMessage = compiledMessage;

      InputStream in = this.getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);

      GoogleCredential serviceAccountCredential = new GoogleCredential.Builder()
        .setTransport(HTTP_TRANSPORT)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(serviceAccountId)
        .setServiceAccountUser(emailToSendWith)
        .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_SEND))
        .setServiceAccountPrivateKeyFromP12File(in)
        .build();

      this.service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, serviceAccountCredential)
                  .setApplicationName(APPLICATION_NAME)
                  .build();
                  
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
    } catch (Exception e) {
      log.warning("general exception");
      e.printStackTrace();
    }
  }


  public void sendEmailsOut(String messageLanguage) {
    this.compiledMessage.compileMessages(messageLanguage);
    String emailBody = compiledMessage.getCompiledBackendMessage();
    PersonEmail contact;
    MimeMessage email;

   
      try { 
         for(PersonEmail contactName : this.contactsList) {
          email = MessagingSetup.createEmail(contactName.getEmail(), this.emailSubject, emailBody);
          MessagingSetup.sendMessage(service, email);
        }
      } catch (MessagingException e) {
        log.warning("error in sending emails out");
      } catch (Exception e) {
        log.warning("general exception");
        e.printStackTrace();
      }
    }

}