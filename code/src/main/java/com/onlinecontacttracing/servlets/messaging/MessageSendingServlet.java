package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.messaging.EmailSender;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.messaging.CompiledMessage;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.PersonEmail;
import java.util.Collections;
import java.security.GeneralSecurityException;
import com.onlinecontacttracing.storage.NotificationBatch;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
* This servlet will send out messages to all the user's approved contacts.
*/
@WebServlet("/send-messages")
public class MessageSendingServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeleteNegativeUserLocationsServlet.class.getName());
  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idTokenString = request.getParameter("idToken");
    String systemMessageName = "VERSION_1";
    String localityResourceName = "US";
    String messageLanguage = "SP";

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

      PositiveUser positiveUser = ofy().load().type(PositiveUser.class).id(userId).now();
      
      String message = "custom message will be retrieved from params";
      CompiledMessage compiledMessage = new CompiledMessage(systemMessage, localityResource, message, positiveUser);

      EmailSender.sendEmailsOut("COVID-19 Updates", compiledMessage, messageLanguage);

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
      ofy().save().entity(notificationBatch).now();

      response.sendRedirect("/send-messages?idToken=" + idTokenString);
      
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.sendRedirect("/?page=login&error=GeneralError");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}