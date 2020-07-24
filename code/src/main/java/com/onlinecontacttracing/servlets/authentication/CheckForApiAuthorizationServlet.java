package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;

@WebServlet("/check-for-api-authorization")
public class CheckForApiAuthorizationServlet extends HttpServlet {
  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    GoogleAuthorizationCodeFlow flow = GetFlow.getFlow();
    // AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/get-token-response").setState(idToken);
    AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri("https://8080-49ecfd50-1d05-462f-af38-ebb02e752a59.us-central1.cloudshell.dev/get-token-response").setState(idToken);
    String url = authUrlRequestProperties.build();
    response.getWriter().println(url);
  }
}