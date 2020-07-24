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
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

@WebServlet("/check-for-api-authorization")
public class CheckForApiAuthorizationServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/contacts.readonly");
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/credentials.json";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create token response and redirect to original url
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      GoogleAuthorizationCodeFlow flow = getFlow();
      TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/check-for-api-authorization").execute();
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
        .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
        .build();
      String idTokenString = request.getParameter("state");
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      String userId = payload.getSubject();
      Credential credential = flow.createAndStoreCredential(tokenResponse, userId);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    GoogleAuthorizationCodeFlow flow = getFlow();
    AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(SCOPES).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/check-for-api-authorization").setState(idToken);
    String url = authUrlRequestProperties.build();
    response.getWriter().println(url);
  }

  public GoogleAuthorizationCodeFlow getFlow() {
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