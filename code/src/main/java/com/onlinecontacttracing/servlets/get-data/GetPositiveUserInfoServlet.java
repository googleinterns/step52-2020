package com.onlinecontacttracing.servlets;

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
public class GetPositiveUserInfoServlet extends HttpServlet {

  /*
  * This servlet will retrieve data from the People Api
  * Additionaly it will forward the request to the servlet for Calendar Api
  * Once both are done, the servlet will merge contact data sets
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    updateUser(request.getParameter("idToken"));

    // Execute runnable to get people data
    ArrayList<PotentialContact> contactsFromPeople = new ArrayList<PotentialContact>();
    Thread peopleInfo = new Thread(new GetPeopleData(contactsFromPeople));
    peopleInfo.start();

    // Forward to servlet to retrieve calendar data
    try {
      request.getRequestDispatcher("/get-positive-user-calendar-info").forward(request,response);

      // Finally, consolidate data sets.
      peopleInfo.join();
      // Load PositiveUserContacts from objectify
      // call mergeContactListsFromPeopleAPI(contactsFromPeople)
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("Positive User done getting info");
  }

  class GetPeopleData implements Runnable {
    ArrayList<PotentialContact> contacts;

    public GetPeopleData(ArrayList<PotentialContact> contacts) {
      this.contacts = contacts;
    }

    public void run() {
      // Get contacts from people api
    }
  }

  public void updateUser(String idToken) {
    // Using dummy function while Cynthia merges Authentication branch
    Optional<Payload> payloadOptional = AuthenticateUser.getUserId(idToken);
    if (payloadOptional.isPresent()) {
      // Get userId form payload
      Payload payload = payloadOptional.get();
      String userId = payload.getSubject();

      // Load user from objectify
      Optional<PositiveUser> positiveUserOptional = Optional.ofNullable(ofy().load().type(PositiveUser.class).id(userId).now());

      // Retrieve user otherwise make new one
      PositiveUser positiveUser;
      if (positiveUserOptional.isPresent()) {
        positiveUser = positiveUserOptional.get();
        positiveUser.setLastLogin();
      } else {
        positiveUser = new PositiveUser(userId, payload.getEmail());
      }

      ofy().save().entity(positiveUser).now();

    } else {
      System.out.println("idToken did not yield payload");
    }
  }
}