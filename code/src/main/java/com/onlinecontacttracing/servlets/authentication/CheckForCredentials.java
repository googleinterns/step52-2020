package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;

import java.util.Collections;



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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.json.webtoken.JsonWebSignature;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import java.util.Optional;

public class CheckForCredentials {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static List<String> SCOPES;
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  public static Optional<Payload> getPayload(HttpServletRequest request, HttpServletResponse response) {

    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
 
      InputStream in = CheckForCredentials.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
              if (in == null) {
                  throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
              }

      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();

      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
          .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m"))
          .build();

        String idTokenString = request.getParameter("idtoken");
        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            Payload payload = idToken.getPayload();
            return Optional.of(payload);
        }
        return Optional.empty();
    } catch (Exception e) {
        e.printStackTrace();
        return Optional.empty();
    } 
  }
  //checks for credentials, if they don't exist, go create them
  public static void createCredentials(HttpServletRequest request, HttpServletResponse response, List<String> scopes, String originalUrl, Optional<Payload> payload) throws IOException {
    HashMap<String, String> userIdAndUrl = new HashMap<> ();
    String jsonOfUserIdAndUrl;
    
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
 
      SCOPES = scopes;
      InputStream in = CheckForCredentials.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
              if (in == null) {
                  throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
              }

      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
     
        if (payload.isPresent()) {
            String userId = payload.get().getSubject();

            Credential credential = new GoogleAuthorizationCodeFlow.Builder(
                  HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build().loadCredential(userId);
            //checks if credentials exist or not, create if not
            if(credential == null) {
              userIdAndUrl.put("userId", userId);
              userIdAndUrl.put("originalUrl", originalUrl);
              jsonOfUserIdAndUrl = new Gson().toJson(userIdAndUrl);

              AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri("get-token-response").setState(jsonOfUserIdAndUrl);
              String url = authUrlRequestProperties.build();

              request.setAttribute("authUrlRequestProperties", authUrlRequestProperties);
              request.setAttribute("flow", flow);

              RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
              requestDispatcher.include(request, response);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }  
  }


}