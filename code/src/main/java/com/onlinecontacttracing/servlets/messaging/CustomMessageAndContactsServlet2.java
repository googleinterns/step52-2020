package com.google.sps.servlets;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Objectify; 
import com.googlecode.objectify.ObjectifyFactory; 
import com.googlecode.objectify.ObjectifyService; 
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUserWithMessage;
import com.onlinecontacttracing.messsaging.GeneratedUserId;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ClassCastException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
/**Servlet to put custom message data into Ofy store */
@WebServlet("/customMessage2")
public class CustomMessageAndContactsServlet2 extends HttpServlet {
  private final ArrayList<String> employeeComment = new ArrayList<String>();
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /**
   * Adds the Emails and custom message to the Objectify store
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int numberOfRecipients = 0;
    try {
      numberOfRecipients = Integer.parseInt(request.getParameter("number-of-recipients-box"));
    } catch(NumberFormatException e) {
        System.err.println("Parsed value from number-of-recipients-box is not a number");
        numberOfRecipients = -1;
    }
    ArrayList<String> emailAddresses = new ArrayList<String>();

    Map<String, String[]> params = request.getParameterMap();
    String userId = new GeneratedUserId("anon-").getIdString();
    PositiveUserWithMessage positiveUserWithMessage = new PositiveUserWithMessage(params, numberOfRecipients, userId);

    ofy().save().entity(positiveUserWithMessage).now();
    response.sendRedirect("/index.html");
  }
}
