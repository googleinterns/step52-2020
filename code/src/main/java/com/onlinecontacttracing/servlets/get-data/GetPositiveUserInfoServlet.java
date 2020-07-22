package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;
import com.onlinecontacttracing.storage.PotentialContact;
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
    ArrayList<PotentialContact> contactsFromPeople = new ArrayList<PotentialContact>();
    Thread peopleInfo = new Thread(new GetPeopleData(contactsFromPeople));
    peopleInfo.start();
    
    try {
      request.getRequestDispatcher("/get-positive-user-calendar-info").forward(request,response);
    } catch(Exception e) {
      e.printStackTrace();
    }

    try {
      peopleInfo.join();
    } catch(Exception e) {
      e.printStackTrace();
    }

    // Finally, consolidate data sets.
    
    // Load PositiveUserContacts from objectify
    // call mergeContactListsFromPeopleAPI(contactsFromPeople)

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
}