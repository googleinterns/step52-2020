package com.onlinecontacttracing.servlets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@WebServlet("/get-calendar-data")
public class GetCalendarDataServlet extends HttpServlet {

  private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
          .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m"))
          .build();

        String idTokenString = request.getParameter("idtoken");

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String userId = payload.getSubject();

            Credential credential = getCredentials(HTTP_TRANSPORT, userId);

            Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
              .setApplicationName(APPLICATION_NAME)
              .build();

            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = service.events().list("primary")
              .setMaxResults(10)
              .setTimeMin(now)
              .setOrderBy("startTime")
              .setSingleEvents(true)
              .execute();



            List<Event> items = events.getItems();
            if (items.isEmpty()) {
                System.out.println("No upcoming events found.");
            } else {
                System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    System.out.printf("%s (%s)\n", event.getSummary(), start);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();

    }
  }

      /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {
        // Load client secrets.
        InputStream in = GetCalendarDataServlet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);//create a class authAPI config constants, have this as a string, eliminate input reader
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      
        Credential credential = new GoogleAuthorizationCodeFlow.Builder(
               HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).build().loadCredential(userId);

        if (credential == null) {
            //response.sendRedirect('/login-servlet')
            //Credential credential = loadCredential(String userId);
        }

        return credential;
    }
}