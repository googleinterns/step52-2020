package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.filters.CheckMessagesForFlags;
import java.util.List;


import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.Collections;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Projection;

@WebServlet("/get-token-response")
public class CheckForCredentialServlet extends HttpServlet {

  private AuthorizationRequestUrl authUrlRequestProperties;
  private GoogleAuthorizationCodeFlow flow;
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    authUrlRequestProperties = request.getAttribute("authUrlRequestProperties");
    flow = request.getAttribute("flow");

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> authUrlProperties = mapper.readValue(authUrlRequestProperties.getState(), new TypeReference<Map<String, Object>>() {});

    TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).execute();
    flow.createAndStoreCredential(tokenResponse, (String) authUrlProperties.get("userId"));

     RequestDispatcher requestDispatcher = request.getRequestDispatcher((String) authUrlProperties.get("originalUrl"));
    requestDispatcher.include(request, response);
  }
}
