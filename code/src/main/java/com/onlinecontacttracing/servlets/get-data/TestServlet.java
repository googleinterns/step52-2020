package com.onlinecontacttracing.storage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.googlecode.objectify.ObjectifyService.ofy;
import java.time.Instant;
import java.util.List;

@WebServlet("/test-phony-data")
public class TestServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    for (int i = 0; i < 100; i++) {
      long t0 = Instant.now().getEpochSecond();
      long t1 = t0 + 60;
      String name = Integer.toString(i);
      NegativeUserPlace place = new NegativeUserPlace(name, name, "Testing Place", t0, t1);
      ofy().save().entity(place).now();
    }
  }
}