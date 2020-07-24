package com.onlinecontacttracing.authentication;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

@WebServlet("/get-token-response")
public class GetTokenResponseServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create token response and redirect to original url
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      GoogleAuthorizationCodeFlow flow = GetFlow.getFlow();
      TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).setRedirectUri("https://covid-catchers-fixed-gcp.ue.r.appspot.com/get-token-response").execute();
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
        .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
        .build();
      String idTokenString = request.getParameter("state");
      GoogleIdToken idToken = verifier.verify(idTokenString);
      Payload payload = idToken.getPayload();
      String userId = payload.getSubject();
      Credential credential = flow.createAndStoreCredential(tokenResponse, userId);

      PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("HI")
                .build();

        // Request 10 connections.
        ListConnectionsResponse listConnectionsResponse = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();

        // Print display name of connections if available.
        List<Person> connections = listConnectionsResponse.getConnections();
        if (connections != null && connections.size() > 0) {
            for (Person person : connections) {
                List<Name> names = person.getNames();
                if (names != null && names.size() > 0) {
                    System.out.println("Name: " + person.getNames().get(0)
                            .getDisplayName());
                } else {
                    System.out.println("No names available for connection.");
                }
            }
        } else {
            System.out.println("No connections found.");
        }

      if (credential == null) {
        response.getWriter().println("We failed");
      } else {
        response.getWriter().println("UWU");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
