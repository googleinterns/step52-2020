package com.onlinecontacttracing.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUser;
import com.google.common.collect.Iterables;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;
import com.googlecode.objectify.Key;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.google.common.collect.Iterables;

@WebServlet("/delete-all-negative-user-data")
public class DeleteAllNegativeUserDataServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
<<<<<<< HEAD:code/src/main/java/com/onlinecontacttracing/servlets/MessageSender.java
  static final Logger log = Logger.getLogger(MessageSender.class.getName());
    
=======
  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";
  static final Logger log = Logger.getLogger(DeleteAllNegativeUserDataServlet.class.getName());

  @Override
>>>>>>> b5c3cadfb9dc01cbf6d2bfb015cb580d8d05ed26:code/src/main/java/com/onlinecontacttracing/servlets/delete-data/DeleteAllNegativeUserDataServlet.java
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

      Iterable<Key<NegativeUserPlace>> negativeUserPlaces = ofy().load().type(NegativeUserPlace.class)
        .filter("userId", userId).keys();
      ofy().delete().keys(negativeUserPlaces).now();

      Iterable<Key<NegativeUserLocation>> negativeUserLocations = ofy().load().type(NegativeUserLocation.class)
        .filter("userId", userId).keys();
      ofy().delete().keys(negativeUserLocations).now();

      response.getWriter().println("/?page=landing&deleted");

    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error" + e.toString());
      response.sendRedirect("/?page=login&error=GeneralError");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}