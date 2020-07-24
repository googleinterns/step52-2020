package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

@WebServlet("/check-for-api-authorization")
public class CheckForApiAuthorizationServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/credentials.json";
  static final Logger log = Logger.getLogger(CheckForApiAuthorizationServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      // Get flow for token response
      GoogleAuthorizationCodeFlow flow = getFlow();

      if (flow == null) {
        response.getWriter().println("Error");
      }

      TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/check-for-api-authorization").execute();

      // Make verifier to get payload
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
        .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
        .build();
      String idTokenString = request.getParameter("state");
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();

      // Get userId form payload and retrieve credential
      String userId = payload.getSubject();
      Credential credential = flow.createAndStoreCredential(tokenResponse, userId);
    
      // API code goes here
      
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.getWriter().println("Error");
    }
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get token to pass into redirect
    String idToken = request.getParameter("idToken");

    // Get flow to build url redirect
    GoogleAuthorizationCodeFlow flow = getFlow();

    if (flow == null) {
      response.getWriter().println("Error");
    }

    AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/check-for-api-authorization").setState(idToken);
    String url = authUrlRequestProperties.build();

    // Send url back to client
    response.getWriter().println(url);
  }

  public GoogleAuthorizationCodeFlow getFlow() {
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      // Create flow object using credentials file
      InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));

      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
      return flow;
    } catch (Exception e) {
      if (e instanceof FileNotFoundException) {
        log.warning("credentials.json not found");
      } else if (e instanceof GeneralSecurityException) {
        log.warning("http transport failed, security error");
      }

      return null;
    }
  }
}