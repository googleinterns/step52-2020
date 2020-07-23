package com.onlinecontacttracing.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.onlinecontacttracing.storage.NegativeUser;
import com.onlinecontacttracing.storage.NegativeUserPlace;
import com.onlinecontacttracing.storage.NegativeUserLocation;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/delete-all-negative-user-data")
public class DeleteAllNegativeUserDataServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");

    // Using dummy function while Cynthia merges Authentication branch
    Optional<Payload> payloadOptional = AuthenticateUser.getUserId(idToken);

    if (payloadOptional.isPresent()) {
      // Get userId form payload
      String userId = payloadOptional.get().getSubject();

      Iterable<Key<NegativeUserPlace>> negativeUserPlaces = ofy().load().type(NegativeUserPlace.class)
        .filter("userId", userId).keys();
      ofy().delete().keys(negativeUserPlaces).now();

      Iterable<Key<NegativeUserLocation>> negativeUserLocations = ofy().load().type(NegativeUserLocation.class)
        .filter("userId", userId).keys();
      ofy().delete().keys(negativeUserLocations).now();

      NegativeUser negativeUser = ofy().load().type(NegativeUser.class).id(userId).now();
      ofy().delete().entity(negativeUser).now();
    }
  }
}