package com.onlinecontacttracing.servlets.redirects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // If user tries to acces notification page they have to login first
    response.sendRedirect("/?page=negative-login");
  }
}
