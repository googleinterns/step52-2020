package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/delete-old-positive-user-places")
public class DeletePositiveUserPlacesServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeletePositiveUserPlacesServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    OldDataDeleter.deleteOldData(OldDataType.POSITIVE_USER_PLACES, log);
  }
}