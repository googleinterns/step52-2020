package com.onlinecontacttracing.servlets;

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
public class GetNegativeUserInfoServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Using dummy function while Cynthia merges Authentication branch
    Optional<Payload> payloadOptional = AuthenticateUser.getUserId(request.getParameter("idToken"));
    if (payloadOptional.isPresent()) {
      // Get userId form payload
      Payload payload = payloadOptional.get();
      String userId = payload.getSubject();

      // Load user from objectify
      Optional<NegativeUser> negativeUserOptional = Optional.ofNullable(ofy().load().type(NegativeUser.class).id(userId).now());

      // Retrieve user otherwise make new one
      NegativeUser negativeUser;
      if (negativeUserOptional.isPresent()) {
        negativeUser = negativeUserOptional.get();
        negativeUser.setLastLogin();
      } else {
        negativeUser = new NegativeUser(userId, payload.getEmail());
      }
      
      ofy().save().entity(negativeUser).now();

      try {
        request.getRequestDispatcher("/get-negative-user-calendar-info").forward(request,response);
      } catch(Exception e) {
        e.printStackTrace();
      }

    } else {
      System.out.println("idToken did not yield payload");
    }


    System.out.println("Negative User done getting info");
    
  }
}