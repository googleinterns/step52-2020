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
import java.util.ArrayList;


@WebServlet("/compile-user-email")
public class CompileUserEmailServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeleteNegativeUserLocationsServlet.class.getName());
  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

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
      
      String systemMessageLanguage = request.getParameter("systemMessageLanguage");
      String localityResourceLanguage = request.getParameter("localityResourceLanguage");
      String customMessage = request.getParameter("customMessage");


      ofy().save().entity(new CustomizableMessage(userId, customMessage)).now();
      SystemMessage systemMessage = SystemMessage.getSystemMessageFromString("VERSION_1");
      LocalityResource localityResource = LocalityResource.getLocalityResourceFromString("US");
      PositiveUser positiveUser = ofy().load().type(PositiveUser.class).id(userId).now();

      CompiledMessage compiledMessageObject = new CompiledMessage(systemMessage,localityResource, customMessage, positiveUser);
      compiledMessageObject.compileMessages(systemMessageLanguage, localityResourceLanguage);
      ArrayList<String> compiledMessage = compiledMessageObject.getCompiledFrontendDisplayMessage();
 
      response.getWriter().println(compiledMessage);
      
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.sendRedirect("/?page=login&error=GeneralError");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}