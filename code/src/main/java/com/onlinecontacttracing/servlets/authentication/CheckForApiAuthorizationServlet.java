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

/**
*  This class directs the creation of user Credentials for accessing APIs.
*/

@WebServlet("/check-for-api-authorization")
public abstract class CheckForApiAuthorizationServlet extends HttpServlet {

  abstract void useCredential(Credential credential);
  // should return "check-for-api-authorization" for now
  abstract String getServletURIName();
  //Update the userId with the newly created credential
  abstract void updateUser(String userId);

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/credentials.json";
  // private static final String url = "https://covid-catchers-fixed-gcp.ue.r.appspot.com";
  private static final String url = "https://8080-ac896ae1-5f0c-45b5-8dfe-19fa4d3d8699.us-east1.cloudshell.dev";
  static final Logger log = Logger.getLogger(CheckForApiAuthorizationServlet.class.getName());

  //Creates the user's credential
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try{
      // Get flow for token response
      GoogleAuthorizationCodeFlow flow = getFlow();
      if (flow == null) {
        response.getWriter().println("Error");
      }
      
      String code = request.getParameter("code");
      String idTokenString = request.getParameter("state");
    
      // Get userId
      String userId = getUserId(idTokenString, flow);
      if (userId.equals("")) {
        response.getWriter().println("Error");
      }
      TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(url+getServletURIName()).execute();
      Credential credential = flow.createAndStoreCredential(tokenResponse, userId);
      updateUser(userId);
      useCredential(credential);
    } catch (Exception e) {
      log.warning("http transport failed, security error");
      response.getWriter().println("Error");
    }
     
  }
  
  //Creates the url for authorizing the user
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get token to pass into redirect
    String idToken = request.getParameter("idToken");
    // Get flow to build url redirect
    GoogleAuthorizationCodeFlow flow = getFlow();

    if (flow == null) {
      response.getWriter().println("Error");
    }

    AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri(url+getServletURIName()).setState(idToken);
    String url = authUrlRequestProperties.build();
    // Send url back to client
    response.getWriter().println(url);
  }

  public String getUserId(String idTokenString, GoogleAuthorizationCodeFlow flow) {
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      // Get flow for token response
      if (flow == null) {
        return "";
      }

      
      // Make verifier to get payload
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
        .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
        .build();
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      // Get userId form payload and retrieve credential
      String userId = payload.getSubject();
      return userId;
      
    } catch (Exception e) {
      log.warning("http transport failed, security error");
      return "";
    }
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