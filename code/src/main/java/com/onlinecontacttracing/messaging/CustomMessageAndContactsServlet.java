// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;


import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
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
import java.util.UUID;
/**Servlet to put custom message data into datastore */
@WebServlet("/customMessage")
public class CustomMessageAndContactsServlet extends HttpServlet {
  private static final ArrayList<String> employeeComment = new ArrayList<String>();
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  //A better way to get the userId is from the payload but that has not been pushed yet
  //Cannot store information in the datastore until wer have userId
  
  /**
   * Get the number of emails to display from the user
   */
  private int getNumEmails(HttpServletRequest request) {
    // Get the input from the form.
    String userNum = request.getParameter("number-of-recipients-box");
    int requestedNum;
    //Convert input into an int
    try {
      requestedNum = Integer.parseInt(userNum);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + userNum);
      return -1;
    }
    return requestedNum;
  }

  /**
   * Adds the Emails and custom message to the datastore
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int numberOfRecipients = 0;
    try {
      numberOfRecipients = Integer.parseInt(request.getParameter("number-of-recipients-box"));
    } catch(NumberFormatException e) {
        System.out.println("Not a number");
        numberOfRecipients = -1;
        //Prompt User for new input
    }
    System.out.println("Got num recipients");
    ArrayList<String> emailAddresses = new ArrayList<String>();
    //Add each email from list of emails to arrayList
    for(int i  = 0; i < numberOfRecipients; i++) {
      emailAddresses.add(request./*getParameter("list-of-emails").*/getParameter("email-box-" + (i + 1)));
    }
    System.out.println("Got emails");
    // try {
    // System.out.println("Try block start");
    // NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    // GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
    //     .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
    //     .build();
    // String idTokenString = request.getParameter("state");
    // System.out.println(idTokenString);
    // GoogleIdToken idToken = verifier.verify(idTokenString);
    // Payload payload = idToken.getPayload();
    // String userId = payload.getSubject();
    // System.out.println("Got Id");

    String userId = new GeneratedUserId("anon").getIdString();
    CustomizableMessage customMessage = new CustomizableMessage(userId, request.getParameter("custom-message-box"));
    
    System.out.println("Made Msg");
    PositiveUserWithMessage positiveUserWithMessage = new PositiveUserWithMessage(emailAddresses, customMessage);

    System.out.println("Make objs");

    ofy().save().entity(positiveUserWithMessage).now();

    System.out.println("Write to ofy save");
    // } catch(Exception e) {
    //     e.printStackTrace();
    // }

    PositiveUserWithMessage result = ofy().load().type(PositiveUserWithMessage.class).id(positiveUserWithMessage.getMessage().getUserId()).now();
    System.out.println("Verify wrote to ofy" + result);
    response.sendRedirect("/index.html");
  }
}
