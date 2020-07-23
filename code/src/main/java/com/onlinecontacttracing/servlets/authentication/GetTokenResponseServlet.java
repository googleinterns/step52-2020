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
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

@WebServlet("/get-token-response")
public class GetTokenResponseServlet extends HttpServlet {

  private AuthorizationRequestUrl authUrlRequestProperties;
  private GoogleAuthorizationCodeFlow flow;
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create token response and redirect to original url
    authUrlRequestProperties = (AuthorizationRequestUrl) request.getAttribute("authUrlRequestProperties");
    flow = (GoogleAuthorizationCodeFlow) request.getAttribute("flow");

    Type authUrlPropertiesType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, Object> authUrlProperties = new Gson().fromJson(authUrlRequestProperties.getState(), authUrlPropertiesType);

    TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).execute();
    flow.createAndStoreCredential(tokenResponse, (String) authUrlProperties.get("userId"));

    RequestDispatcher requestDispatcher = request.getRequestDispatcher((String) authUrlProperties.get("originalUrl"));
    try {
      requestDispatcher.include(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
