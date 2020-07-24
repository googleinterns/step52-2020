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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.googlecode.objectify.annotation.Id;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.storage.PositiveUser;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ClassCastException;
import java.util.ArrayList;
/**Servlet to put custom message data into datastore */
@WebServlet("/customMessage")
public class CustomMessageAndContactsServlet extends HttpServlet {
  ArrayList<String> employeeComment = new ArrayList<String>();
  @Id private String userId;
  //A better way to get the userId is from the payload but that has not been pushed yet
  //Cannot store information in the datastore until wer have userId
  public CustomMessageAndContactsServlet(HttpServletRequest request, HttpServletResponse response, PositiveUser positiveUser) {
    userId = positiveUser.getUserId();
  }
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
    ArrayList<String> emailAddresses = new ArrayList<String>();
    //Add each email from list of emails to arrayList
    for(int i  = 0; i < numberOfRecipients; i++) {
      emailAddresses.add(request./*getParameter("list-of-emails").*/getParameter("email-box-" + (i + 1)));
    }
    CustomizableMessage customMessage = new CustomizableMessage(userId, request.getParameter("custom-message-box"));
    
    Entity positiveUserWithMessage = new Entity("Positive User with Message");
    positiveUserWithMessage.setProperty("List of Contacts", emailAddresses);
    positiveUserWithMessage.setProperty("Custom Message", customMessage);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(positiveUserWithMessage);

    response.sendRedirect("/index.html");
  }
}
