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
  
  void useCredential(Credential credential, HttpServletResponse response) throws IOException, InterruptedException {
    Thread contactInfo = new Thread(new GetCalendarDataForNegativeUser(credential));

    contactInfo.start();
  
    contactInfo.join();

    response.sendRedirect("/?page=notification");
  }



  void updateUser(String userId, String email) {
    // Load user from objectify
    Optional<NegativeUser> negativeUserOptional = Optional.ofNullable(ofy().load().type(NegativeUser.class).id(userId).now());

    NegativeUser negativeUser = negativeUserOptional.map(user -> {
      // If user found, update login
      user.setLastLogin();
      return user;
    }).orElse(
      // If user is not found, make new one
      new NegativeUser(userId, email)
    );

    ofy().save().entity(negativeUser).now();
  }
}