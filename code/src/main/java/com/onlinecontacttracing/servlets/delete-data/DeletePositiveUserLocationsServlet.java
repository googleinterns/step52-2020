package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.logging.Logger;

@WebServlet("/delete-old-positive-user-locations")
public class DeletePositiveUserLocationsServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeletePositiveUserLocationsServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int numberOfDeletions = OldDataDeleter.deleteOldData(OldDataTypes.POSITIVE_USER_LOCATIONS);
    log.info("Number of deleted positive user locations: " + numberOfDeletions);
  }
}