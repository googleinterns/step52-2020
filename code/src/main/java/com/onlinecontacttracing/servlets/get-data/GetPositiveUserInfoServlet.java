package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;

@WebServlet("/get-positve-user-info")
public class GetPositiveUserInfoServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ForwardRequest peopleInfo = new ForwardRequest("/get-positive-user-people-info", request, response);
    ForwardRequest contactInfo = new ForwardRequest("/get-positive-user-calendar-info", request, response);

    peopleInfo.start();
    contactInfo.start();

    // Wait for servlets to finish retrieving data.
    try {
      peopleInfo.join();
      contactInfo.join();
    } catch(Exception e) {
        e.printStackTrace();
    }

    // Finally, consolidate data sets.
    try {
      request.getRequestDispatcher("/merge-positive-user-contacts").forward(request, response);
    } catch(Exception e) {
      e.printStackTrace();
    }
    System.out.println("Positive User done getting info");
  }

  class ForwardRequest extends Thread {
    String servlet;
    HttpServletRequest request;
    HttpServletResponse response;

    public ForwardRequest(String servlet, HttpServletRequest request, HttpServletResponse response) {
      this.servlet = servlet;
      this.request = request;
      this.response = response;
    }

    public void run() {
      try {
        request.getRequestDispatcher(servlet).forward(request, response);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
}