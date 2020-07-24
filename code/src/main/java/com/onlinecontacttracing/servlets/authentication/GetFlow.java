package com.onlinecontacttracing.authentication;

import java.util.Collections;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.TokenResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.io.FileInputStream;
import java.io.File;

class GetFlow {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/credentials.json";

  public static GoogleAuthorizationCodeFlow getFlow() {
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
 
      InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));

      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
      return flow;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}