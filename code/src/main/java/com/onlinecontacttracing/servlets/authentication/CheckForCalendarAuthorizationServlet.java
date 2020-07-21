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

@WebServlet("/check-for-calendar-credentials")
public class CheckForCalendarAuthorizationServlet extends HttpServlet {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  private GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build();
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    if (request.getAttribute("authUrlRequestProperties") == null) {
      CheckForCredentials.createCredentials(request, response, SCOPES, "check-for-contacts-credentials");
    } else {

      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> authUrlProperties = mapper.readValue(authUrlRequestProperties.getState(), new TypeReference<Map<String, Object>>() {});
      String userId = (String) authUrlProperties.get("userId");
      Credential credential = flow.loadCredential(userId);
      if(credential == null) {
        PrintWriter out = response.getWriter();
		    out.println("Something went wrong, please proceed to manual input.");
      } else {
        //API setup
      }
    }
  }


}