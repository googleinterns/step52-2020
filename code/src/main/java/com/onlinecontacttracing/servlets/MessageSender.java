package com.onlinecontacttracing.servlets;

import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.Iterator;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.googlecode.objectify.ObjectifyService;
import com.onlinecontacttracing.storage.PersonEmail;
import com.onlinecontacttracing.storage.NotificationBatch;
import java.util.Collection;
import java.util.Collections;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.google.api.client.json.JsonFactory;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.security.GeneralSecurityException;

@WebServlet("/message-sender")
public class MessageSender extends HttpServlet {

  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  static final Logger log = Logger.getLogger(MessageSender.class.getName());
    
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
