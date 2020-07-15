package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.logging.Logger;

@WebServlet("/delete-old-notification-batch")
public class DeleteNotificationBatchServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeleteNotificationBatchServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    OldDataDeleter.deleteOldData(OldDataTypes.NOTIFICATION_BATCH, log);
  }
}