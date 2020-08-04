package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.PositiveUser;
import java.util.Optional;
import java.util.Set;

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
  void useCredential(String userId, Credential credential, HttpServletResponse response) throws IOException, InterruptedException {
    // Execute runnable to get people data
    CalendarDataForPositiveUser calendarDataForPositiveUser = new CalendarDataForPositiveUser(ofy(), userId, credential);
    Thread peopleInfo = new Thread(peopleDataForPositiveUser);
    Thread contactInfo = new Thread(calendarDataForPositiveUser);

    peopleInfo.start();
    contactInfo.start();
    
    peopleInfo.join();
    contactInfo.join();

    // TODO Load PositiveUserContacts from objectify
    PositiveUserContacts fullContacts = ofy.load().type(PositiveUserContacts.class).id(userId).now();
    // Merge contacts from Calendar and People APIs
    fullContacts.mergeContactListsFromPeopleAPI(calendarDataForPositiveUser.getContacts());
    ofy.save().entity(fullContacts).now();
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