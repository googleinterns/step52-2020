package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import com.onlinecontacttracing.authentication.CheckForApiAuthorizationServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.onlinecontacttracing.storage.PositiveUser;

import com.googlecode.objectify.Objectify; 
import com.googlecode.objectify.ObjectifyFactory; 
import com.googlecode.objectify.ObjectifyService; 
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.EmailSender;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.messaging.CompiledMessage;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import com.google.cloud.authentication.serviceaccount.CreateServiceAccountKey;
import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.PersonEmail;
import com.onlinecontacttracing.storage.BusinessNumber;
import java.util.Collections;
import java.security.GeneralSecurityException;


@WebServlet("/send-messages")
public class MessageSendingServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeleteNegativeUserLocationsServlet.class.getName());
  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idTokenString = request.getParameter("idToken");
    // String systemMessageName = request.getParameter("systemMessage");
    // String localityResourceName = request.getParameter("localityResource");
    // String messageLanguage = request.getParameter("messageLanguage");
    String systemMessageName = "VERSION_1";
    String localityResourceName = "US";
    String messageLanguage = "SP";

    // CreateServiceAccountKey.createKey("covid-catchers-fixed-gcp");
    SystemMessage systemMessage = SystemMessage.getSystemMessageFromString(systemMessageName);
    LocalityResource localityResource = LocalityResource.getLocalityResourceFromString(localityResourceName);

    try {
      NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // Make verifier to get payload
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      String userId = payload.getSubject();

      NotificationBatch notificationInfo = ofy().load().type(NotificationBatch.class).id(userId).now();
      PositiveUser positiveUser = ofy().load().type(PositiveUser.class).id(userId).now();

      ArrayList<PersonEmail> contactsList = notificationInfo.getPersonEmails();
      
      // TODO: replace with saved email
      CustomizableMessage customizableMessage = new CustomizableMessage(userId, "hi cynthia!!!");

      CompiledMessage compiledMessage = new CompiledMessage(systemMessage, localityResource, customizableMessage, positiveUser);//fix the enum resources
      EmailSender emailSender = new EmailSender("COVID-19 Updates", contactsList, compiledMessage); 
      emailSender.sendEmailsOut(messageLanguage);

    } catch(Exception e) {
        e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      // Set up
      NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();

      // Get payload with userId
      String idTokenString = request.getParameter("idToken");
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      String userId = payload.getSubject();

      // Get emails to populate notificationBatch
      String[] emails = request.getParameter("emails").split(",");

      NotificationBatch notificationBatch = new NotificationBatch(userId);
      for (String email : emails) {
        notificationBatch.addPersonEmail(email);
      }
      
      // Store notification batch
      ObjectifyService.ofy().save().entity(notificationBatch).now();

      response.sendRedirect("/send-messages?idToken=" + idTokenString);
      
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.sendRedirect("/?page=login&error=GeneralError");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}