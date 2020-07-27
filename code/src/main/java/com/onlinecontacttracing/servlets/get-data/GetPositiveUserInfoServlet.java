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

@WebServlet("/get-positve-user-info")
public class GetPositiveUserInfoServlet extends CheckForApiAuthorizationServlet {

  String getServletURIName() {
    return "/get-positve-user-info";
  }
  
  
  /*
  * This servlet will retrieve data from the People Api
  * Additionaly it will forward the request to the servlet for Calendar Api
  * Once both are done, the servlet will merge contact data sets
  */
  void useCredential(Credential credential) throws InterruptedException{
    System.out.println("Postive useCred");
    // Execute runnable to get people data
    ArrayList<PotentialContact> contactsFromPeople = new ArrayList<PotentialContact>();
    Thread peopleInfo = new Thread(new GetPeopleDataForPositiveUser(credential, contactsFromPeople));
    Thread contactInfo = new Thread(new GetCalendarDataForPositiveUser(credential));

    peopleInfo.start();
    contactInfo.start();

    
    peopleInfo.join();
    contactInfo.join();

    // Load PositiveUserContacts from objectify
    // call mergeContactListsFromPeopleAPI(contactsFromPeople)
  }

  void updateUser(String userId) {
    System.out.println("Postive user");
    // Load user from objectify
    Optional<PositiveUser> positiveUserOptional = Optional.ofNullable(ofy().load().type(PositiveUser.class).id(userId).now());

    // Retrieve user otherwise make new one
    PositiveUser positiveUser;
    if (positiveUserOptional.isPresent()) {
      positiveUser = positiveUserOptional.get();
      positiveUser.setLastLogin();
    } else {
      positiveUser = new PositiveUser(userId, "payload.getEmail()");
    }

    ofy().save().entity(positiveUser).now();
  }
}