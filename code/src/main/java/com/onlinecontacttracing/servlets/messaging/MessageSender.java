package com.onlinecontacttracing.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.logging.Logger;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import com.onlinecontacttracing.storage.NotificationBatch;
import com.onlinecontacttracing.storage.PersonEmail;
import static com.googlecode.objectify.ObjectifyService.ofy;

@WebServlet("/message-sender")
public class MessageSender extends HttpServlet {

  static final Logger log = Logger.getLogger(MessageSender.class.getName());
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String CLIENT_ID = "1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com";

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String idTokenString = request.getParameter("idToken");
      NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // Make verifier to get payload
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      String userId = payload.getSubject();

      NotificationBatch notificationBatch = new NotificationBatch(userId);

      String[] emails = request.getParameter("emails").split(",");
      for (String email : emails) {
        notificationBatch.addPersonEmail(email);
      }
      for (PersonEmail p : notificationBatch.getPersonEmails()) Sytem.out.println(p.getEmail());
      ofy().save().entity(notificationBatch).now();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}