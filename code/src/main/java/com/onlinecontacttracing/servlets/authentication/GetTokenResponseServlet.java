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
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
// import com.google.api.client.util.FileDataStoreFactory;
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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@WebServlet("/get-token-response")
public class GetTokenResponseServlet extends HttpServlet {

  private AuthorizationRequestUrl authUrlRequestProperties;
  private GoogleAuthorizationCodeFlow flow;
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    authUrlRequestProperties = (AuthorizationRequestUrl) request.getAttribute("authUrlRequestProperties");
    flow = (GoogleAuthorizationCodeFlow) request.getAttribute("flow");

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> authUrlProperties = mapper.readValue(authUrlRequestProperties.getState(), new TypeReference<Map<String, Object>>() {});

    TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).execute();
    flow.createAndStoreCredential(tokenResponse, (String) authUrlProperties.get("userId"));

    RequestDispatcher requestDispatcher = request.getRequestDispatcher((String) authUrlProperties.get("originalUrl"));
    requestDispatcher.include(request, response);
  }
}
