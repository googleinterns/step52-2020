package com.onlinecontacttracing.authentication;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;

import java.util.Collections;

@WebServlet("/get-token-response")
public class GetTokenResopnseServlet extends HttpServlet {

  private AuthorizationRequestUrl authUrlRequestProperties;
  private GoogleAuthorizationCodeFlow flow;
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    authUrlRequestProperties = request.getAttribute("authUrlRequestProperties");
    flow = request.getAttribute("flow");

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> authUrlProperties = mapper.readValue(authUrlRequestProperties.getState(), new TypeReference<Map<String, Object>>() {});

    TokenResponse tokenResponse = flow.newTokenRequest(request.getParameter("code")).execute();
    flow.createAndStoreCredential(tokenResponse, (String) authUrlProperties.get("userId"));

     RequestDispatcher requestDispatcher = request.getRequestDispatcher((String) authUrlProperties.get("originalUrl"));
    requestDispatcher.include(request, response);
  }
}
