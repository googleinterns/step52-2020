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
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import java.util.HashMap;
import java.util.Map;
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
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import java.util.Optional;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

@WebServlet("/check-for-api-authroization")
abstract class CheckForApiAuthorizationServlet extends HttpServlet {
  abstract List<String> getScopes();
  abstract void onSuccessfulLogin(Credential credential);
  abstract String getOriginalUrl();
  abstract String getCredentialsFilePath();
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String credentialsFilePath = getCredentialsFilePath();
      InputStream in = CheckForApiAuthorizationServlet.class.getResourceAsStream(getCredentialsFilePath());
          if (in == null) {
              throw new FileNotFoundException("Resource not found: " + getCredentialsFilePath());
          }
      if (request.getAttribute("authUrlRequestProperties") == null) {
        Optional<Payload> payload = CheckForCredentials.getPayload(request, response, getScopes());
        CheckForCredentials.createCredentials(request, response, getScopes(), getOriginalUrl(), payload);
      } else {
        AuthorizationRequestUrl authUrlRequestProperties = (AuthorizationRequestUrl) request.getAttribute("authUrlRequestProperties");
        GoogleAuthorizationCodeFlow flow = (GoogleAuthorizationCodeFlow) request.getAttribute("flow");

        Type authUrlPropertiesType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, Object> authUrlProperties = new Gson().fromJson(authUrlRequestProperties.getState(), authUrlPropertiesType);
        String userId = (String) authUrlProperties.get("userId");
        Credential credential = flow.loadCredential(userId);
        if(credential == null) {
          response.sendError(403);
        } else {
          onSuccessfulLogin(credential);
        }
      }
    
  }

}