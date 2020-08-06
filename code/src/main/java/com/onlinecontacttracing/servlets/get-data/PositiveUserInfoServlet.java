package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.gson.Gson;
import static com.googlecode.objectify.ObjectifyService.ofy;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinecontacttracing.authentication.AuthenticationScope;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import com.onlinecontacttracing.storage.PotentialContact;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.List;

@WebServlet("/get-positive-user-info")
public class PositiveUserInfoServlet extends CheckForApiAuthorizationServlet {

  @Override
  String getServletURIName() {
    return "/get-positive-user-info";
  }
  
  /*
  * This servlet will retrieve data from the People Api
  * Additionaly it will forward the request to the servlet for Calendar Api
  * Once both are done, the servlet will merge contact data sets
  */
  @Override
  void useCredential(AuthorizationRoundTripState state, Credential credential, HttpServletResponse response) throws IOException, InterruptedException {
    // Execute runnable to get people data
    CalendarDataForPositiveUser calendarDataForPositiveUser = new CalendarDataForPositiveUser(ofy(), state.userId, credential);
    Thread peopleInfo = new Thread(new PeopleDataForPositiveUser(ofy(), state.userId, credential));
    Thread calendarInfo = new Thread(calendarDataForPositiveUser);

    if (state.authenticationScopes.contains(AuthenticationScope.CALENDAR)) {
      calendarInfo.start();
    }

    if (state.authenticationScopes.contains(AuthenticationScope.CONTACTS)) {
      peopleInfo.start();
    }
    
    peopleInfo.join();
    calendarInfo.join();

    PositiveUserContacts fullContacts = ofy().load().type(PositiveUserContacts.class).id(state.userId).now();

    if(fullContacts == null) {
        fullContacts = new PositiveUserContacts(state.userId);
    }

    // Merge contacts from Calendar and People APIs
    fullContacts.mergeContactListsFromCalendarAPI(calendarDataForPositiveUser.getContacts());

    ofy().save().entity(fullContacts).now();

    Gson gson = new Gson();
    response.sendRedirect("/JSP/approve.jsp?authState=" + gson.toJson(state));
  }

  @Override
  void updateUser(String userId, String email) {
    // Load user from objectify
    Optional<PositiveUser> positiveUserOptional = Optional.ofNullable(ofy().load().type(PositiveUser.class).id(userId).now());

    PositiveUser positiveUser = positiveUserOptional.map(user -> {
      // If user found, update login
      user.setLastLogin();
      return user;
    }).orElse(
      // If user is not found, make new one
      new PositiveUser(userId, email)
    );

    ofy().save().entity(positiveUser).now();
  }
}