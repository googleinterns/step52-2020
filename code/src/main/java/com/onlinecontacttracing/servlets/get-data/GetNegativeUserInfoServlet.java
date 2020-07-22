package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/get-negative-user-info")
public class GetNegativeUserInfoServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      request.getRequestDispatcher("/get-negative-user-calendar-info").forward(request,response);
    } catch(Exception e) {
      e.printStackTrace();
    }
    System.out.println("Negative User done getting info");
  }
}