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

@WebServlet("/create-credentials")
public class CreateCredentialsServlet extends HttpServlet {

  private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  private GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
               HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
 

  // public CreateCredentialsServlet(String scope, String applicationName) {
  //   APPLICATION_NAME = applicationName;
  //   SCOPES = Collections.singletonList(scope);
  //   flow = new GoogleAuthorizationCodeFlow.Builder(
  //              HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
  // }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String url = flow.newAuthorizationUrl().setScopes(SCOPE)
        .setRedirectUri("get-token-response").build();
    response.sendRedirect(url);
  }
}