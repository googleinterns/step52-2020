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
    try {
      request.getRequestDispatcher("/get-positive-user-people-info").forward(request,response);
      request.getRequestDispatcher("/get-positive-user-calendar-info").forward(request,response);
    } catch(Exception e) {
      e.printStackTrace();
    }
    // Wait for servlets to finish executing?
    try {
      request.getRequestDispatcher("/merge-positive-user-contacts").forward(request,response);
    } catch(Exception e) {
      e.printStackTrace();
    }
    System.out.println("Positive User done getting info");
  }
}