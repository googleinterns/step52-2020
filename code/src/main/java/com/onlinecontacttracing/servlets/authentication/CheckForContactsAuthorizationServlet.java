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
import com.onlinecontacttracing.authentication.CheckForApiAuthorizationServlet;


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

@WebServlet("/check-for-contacts-credentials")
public class CheckForContactsAuthorizationServlet extends CheckForApiAuthorizationServlet {

  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  
  List<String> getScopes() {
    return SCOPES;
  }

  String getOriginalUrl() {
    return "check-for-contacts-credentials";
  }

  String getCredentialsFilePath() {
    return CREDENTIALS_FILE_PATH;
  }

  void onSuccessfulLogin(Credential credential) {
    
  }



}