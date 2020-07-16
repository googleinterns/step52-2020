package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.Message;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.messaging.MessagingSetup;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SendEmails {

  //get contacts list from objectify
  //create message
  //create instance of messaging setup and try to email
  //store results in database
  private String emailSubject;
  private String emailBody;
  private Message messageObject;
  private ArrayList<PotentialContact> contactsList;
  private Gmail service;
  private PositiveUser user;

  private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  
  public SendEmails (SystemMessage systemMessage, LocalityResource localityResource, CustomizableMessage customizableMessage, String emailSubject, String messageLanguage, ArrayList<PotentialContact> contactsList, PositiveUser user) {
    messageObject = new Message(systemMessage, localityResource, customizableMessage, user);
    emailBody = messageObject.compileMessage(messageLanguage);
    this.emailSubject= emailSubject;
    this.contactsList = contactsList;
    // service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
    //             .setApplicationName(APPLICATION_NAME)
    //             .build();
    this.user = user;
  }

  public void sendEmailsOut () {
    PotentialContact contact;
    MimeMessage email;
    for(PotentialContact contactName : contactsList) {
      email = MessagingSetup.createEmail(contactName.getEmail(), user.getUserEmail(), emailSubject, emailBody);
      // sendMessage(service, user.getUserId(), email);
    }
  }

  // private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
  //       // Load client secrets
  //       InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
  //       if (in == null) {
  //           throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
  //       }
  //       GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

  //       // Build flow and trigger user authorization request.
  //       GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
  //               HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
  //               .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
  //               .setAccessType("offline")
  //               .build();
  //       LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
  //       return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  //   }

}