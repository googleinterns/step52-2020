package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.CheckMessagesForFlags;
import java.util.List;


import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.Collections;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Projection;

@WebServlet("/check-for-credentials")
public class CheckForCredentialServlet extends HttpServlet {

  //private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  private GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
               HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
          .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m"))
          .build();

        String idTokenString = request.getParameter("idtoken");

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String userId = payload.getSubject();

            InputStream in = CheckForCredentialServlet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);//create a class authAPI config constants, have this as a string, eliminate input reader
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }

            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
          
            Credential credential = new GoogleAuthorizationCodeFlow.Builder(
                  HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build().loadCredential(userId);
            if (credential != null) {
              response.getWriter().println(credential);//this should get changed
            }
            else{
              response.sendRedirect('create-credentials');
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }  
  }


}