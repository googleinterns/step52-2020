package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.NegativeUser;
import java.util.Optional;

@WebServlet("/get-negative-user-info")
public class GetNegativeUserInfoServlet extends CheckForApiAuthorizationServlet {

  String getServletURIName() {
    return "/get-negative-user-info";
  }
  
  void useCredential(Credential credential) {
    System.out.println("Negative useCred");
    Thread contactInfo = new Thread(new GetCalendarDataForNegativeUser(credential));

    contactInfo.start();

    try {
      contactInfo.join();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }



  void updateUser(String userId) {
    System.out.println("Negative updateUser");
    // Load user from objectify
    Optional<NegativeUser> negativeUserOptional = Optional.ofNullable(ofy().load().type(NegativeUser.class).id(userId).now());

    // Retrieve user otherwise make new one
    NegativeUser negativeUser;
    if (negativeUserOptional.isPresent()) {
      negativeUser = negativeUserOptional.get();
      negativeUser.setLastLogin();
    } else {
      negativeUser = new NegativeUser(userId, "payload.getEmail()");
    }
    
    ofy().save().entity(negativeUser).now();
  }
}