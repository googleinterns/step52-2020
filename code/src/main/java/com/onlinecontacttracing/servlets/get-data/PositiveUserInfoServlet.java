package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.PositiveUser;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import com.onlinecontacttracing.authentication.AuthenticationScope;

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

    // TODO Load PositiveUserContacts from objectify
    PositiveUserContacts p = new PositiveUserContacts(state.userId);
    p.mergeContactListsFromCalendarAPI(calendarDataForPositiveUser.getContacts());
    ofy().save().entity(p).now();
 
    response.sendRedirect("/JSP/approve.jsp?idToken=" + state.idToken + "&timeZoneOffset=" + state.timeZoneOffset);
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