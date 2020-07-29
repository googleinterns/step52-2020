package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.onlinecontacttracing.storage.PotentialContact;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.PositiveUser;
import java.util.Optional;
import java.util.ArrayList;

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
    ArrayList<PotentialContact> contactsFromPeople = new ArrayList<PotentialContact>();
    Thread peopleInfo = new Thread(new PeopleDataForPositiveUser(credential, contactsFromPeople));
    Thread contactInfo = new Thread(new CalendarDataForPositiveUser(ofy(), userId, credential));

    peopleInfo.start();
    contactInfo.start();
    
    peopleInfo.join();
    contactInfo.join();

    // TODO Load PositiveUserContacts from objectify
    // TODO call mergeContactListsFromPeopleAPI(contactsFromPeople)
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