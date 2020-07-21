package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;

import java.util.Collections;
import com.onlinecontacttracing.authentication.CheckForCredentials;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.servlet.RequestDispatcher;
import com.google.api.services.calendar.CalendarScopes;
import java.io.PrintWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@WebServlet("/check-for-calendar-credentials")
public class CheckForCalendarAuthorizationServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try{
      final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  
      InputStream in = CheckForCalendarAuthorizationServlet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
          if (in == null) {
              throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
          }
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      
      if (request.getAttribute("authUrlRequestProperties") == null) {
        CheckForCredentials.createCredentials(request, response, SCOPES, "check-for-contacts-credentials");
      } else {
        AuthorizationRequestUrl authUrlRequestProperties = (AuthorizationRequestUrl) request.getAttribute("authUrlRequestProperties");
        GoogleAuthorizationCodeFlow flow = (GoogleAuthorizationCodeFlow) request.getAttribute("flow");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> authUrlProperties = mapper.readValue(authUrlRequestProperties.getState(), new TypeReference<Map<String, Object>>() {});
        String userId = (String) authUrlProperties.get("userId");
        Credential credential = flow.loadCredential(userId);
        if(credential == null) {
          PrintWriter out = response.getWriter();
          out.println("Something went wrong, please proceed to manual input.");
        } else {
          //API setup
        }
      }
    } catch (Exception e){

    }
  }


}