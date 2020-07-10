package com.google.sps.servlets.redirects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.google.sps.storage.NotificationBatch;
import com.google.sps.storage.Constants;
import com.google.common.collect.Iterables;

@WebServlet("/delete-old-notification-batch")
public class DeleteNotificationBatchServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Iterable<Key<NotificationBatch>> allKeys = ofy().load().type(NotificationBatch.class).filter("timeCreatedSeconds <", Instant.now().getEpochSecond()-Constants.NOTIFICATION_BATCH_MAX_TIME).keys();

    try {
      FileWriter myWriter = new FileWriter("test.log");
      myWriter.write("Deleted old batches at time: " + Iterables.size(allKeys));
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    ofy().delete().keys(allKeys);
  }
}
