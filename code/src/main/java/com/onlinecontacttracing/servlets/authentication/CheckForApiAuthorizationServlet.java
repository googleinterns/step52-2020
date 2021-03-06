package com.onlinecontacttracing.authentication;
 
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
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
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.HashMap;
import com.google.gson.Gson;
 
/**
*  This class directs the creation of user Credentials for accessing APIs.
*/

public abstract class CheckForApiAuthorizationServlet extends HttpServlet {
 
  // access API with the created credential
  abstract void useCredential(AuthorizationRoundTripState authorizationRoundTripState, Credential credential, HttpServletResponse response) throws IOException, InterruptedException;
  // URI pointing to redirect to the servlet that implements this class
  abstract String getServletURIName();
  // Update the userId with the newly created credential
  abstract void updateUser(String userId, String email);
  
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String CREDENTIALS_FILE_PATH = "WEB-INF/credentials.json";

  private static final String url = "https://online-contact-tracing.ue.r.appspot.com";
  private static final String CLIENT_ID = "83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com";

  static final Logger log = Logger.getLogger(CheckForApiAuthorizationServlet.class.getName());
 
  /**
  *  This method creates the user's Credential. All errors will be logged and 
  *  dealt with by the frontend. If no errors, the created credential is used immediately.
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      // Get parameters from request
      String code = request.getParameter("code");
      String authorizationRoundTripStateAsJson = request.getParameter("state");
 
      // Parse state parameter from Json string to AuthorizationRoundTripState class
      Gson gson = new Gson();
 
      AuthorizationRoundTripState authorizationRoundTripState = gson.fromJson(authorizationRoundTripStateAsJson, AuthorizationRoundTripState.class);
 
      List<String> scopes = authorizationRoundTripState.getScopeNames();
 
      // Get flow for token response
      GoogleAuthorizationCodeFlow flow = getFlow(scopes);
 
      // Get payload containing user info
      Payload payload = getPayload(authorizationRoundTripState.idToken, flow);
 
      // Get userId form payload and retrieve credential
      authorizationRoundTripState.userId = payload.getSubject();
      String email = payload.getEmail();
      
      TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(url + getServletURIName()).execute();

      
      Credential credential = flow.createAndStoreCredential(tokenResponse, authorizationRoundTripState.userId);
      updateUser(authorizationRoundTripState.userId, email);
      useCredential(authorizationRoundTripState, credential, response);
//       response.getWriter().println(email);


    } catch (FileNotFoundException e) {
      log.warning("credentials.json not found");
      response.sendRedirect("/?page=login&error=FileError");
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.sendRedirect("/?page=login&error=GeneralError");
    } catch(Exception e) { // don't expect any other error
      log.warning("An exception occurred: " + e.toString());
      response.sendRedirect("/?page=login&error=GeneralError");
    }
  }
  
  /**
  *  This method creates the URL to authorize access to the user's data. All errors will be logged
  *  and dealt with by the frontend. If no errors, the URL is sent to the frontend for use.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      // Get token to pass into redirect
      String idToken = request.getParameter("idToken");
      String timeZoneOffset = request.getParameter("timeZoneOffset");
      boolean calendar = Boolean.parseBoolean(request.getParameter("calendar"));
      boolean contacts = Boolean.parseBoolean(request.getParameter("contacts"));
      
      AuthorizationRoundTripState authorizationRoundTripState = new AuthorizationRoundTripState(idToken, timeZoneOffset);
      authorizationRoundTripState.addScope(calendar, AuthenticationScope.CALENDAR);
      authorizationRoundTripState.addScope(contacts, AuthenticationScope.CONTACTS);
   
      List<String> scopes = authorizationRoundTripState.getScopeNames();
 
      // Get flow to build url redirect
      GoogleAuthorizationCodeFlow flow = getFlow(scopes);
 
      Gson gson = new Gson();
      AuthorizationRequestUrl authUrlRequestProperties = flow.newAuthorizationUrl().setScopes(scopes).setRedirectUri(url + getServletURIName()).setState(gson.toJson(authorizationRoundTripState));
      String url = authUrlRequestProperties.build();

      // Send url back to client
      response.getWriter().println(url);
 
    } catch (FileNotFoundException e) {
      log.warning("credentials.json not found");
      response.sendRedirect("/?page=login&error=FileError");
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error");
      response.sendRedirect("/?page=login&error=TransportError");
    } catch(Exception e) { //don't expect any other error
      e.printStackTrace();
      log.warning("exception occurred: " + e.toString());
      response.sendRedirect("/?page=login&error=GeneralError");
    }
  }
  
  private Payload getPayload(String idTokenString, GoogleAuthorizationCodeFlow flow) throws IOException, GeneralSecurityException {
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    
    // Make verifier to get payload
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
    .setAudience(Collections.singletonList(CLIENT_ID))
    .build();
    GoogleIdToken idToken = verifier.verify(idTokenString);
    return idToken.getPayload();
  }
 
  /**
  *  This method returns an GoogleAuthorizationCodeFlow object.
  */
  private GoogleAuthorizationCodeFlow getFlow(List<String> scopes) throws IOException, FileNotFoundException, GeneralSecurityException {
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
 
    // Create flow object using credentials file
    InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
 
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, scopes).build();
    return flow;

  }
}
