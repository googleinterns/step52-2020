<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.google.api.client.googleapis.javanet.GoogleNetHttpTransport,
com.google.api.client.http.javanet.NetHttpTransport,
com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier,
java.util.Collections,
java.util.Optional,
com.google.gson.Gson,
com.google.api.client.json.JsonFactory,
com.google.api.client.json.jackson2.JacksonFactory,
com.google.api.client.googleapis.auth.oauth2.GoogleIdToken,
com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload,
static com.googlecode.objectify.ObjectifyService.ofy,
com.onlinecontacttracing.storage.NotificationBatch,
com.onlinecontacttracing.storage.PersonEmail,
com.onlinecontacttracing.authentication.AuthorizationRoundTripState" %>
<!DOCTYPE html>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" href="../CSS/approval.css">
   <script src="../JavaScript/approval.js"></script>
   <title>Approve Contacts</title>
</head>
   
<body onload="removeLines()">
  <div id="wrapper-background">
    <div id="line-vertical-left" class="line line-vertical"></div>
    <div id="line-vertical-right" class="line line-vertical"></div>
    <div id="line-horizontal-top" class="line line-horizontal"></div>
    <div id="line-horizontal-bottom" class="line line-horizontal"></div>
  <% 
    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    String idTokenString = request.getParameter("idToken");

    // Make verifier to get payload
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
    .setAudience(Collections.singletonList("83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com"))
    .build();
    GoogleIdToken idToken = verifier.verify(idTokenString);
    Payload payload = idToken.getPayload();
    String userId = payload.getSubject();

    NotificationBatch batch = ofy().load().type(NotificationBatch.class).id(userId).now();
    if (batch != null && batch.getPersonEmails() != null) {
  %>
      <p class="mission-statement"> The following is the status of the emails: </p>
      <div class="picker header">
        <p class="email"> Email </p>
        <p> Status </p>
      </div>
      <div class="list">
  <% 
      for (PersonEmail email : batch.getPersonEmails()) {
  %>
      <div class="picker contact">
        <p> <%=  email.hasBeenContactedSuccessfully()%> </p>
        <p class="email"> <%= email.getEmail() %> </p>
      </div>
  <%
      }
  %>
      </div>
  <%
    } else {
  %>
    <h1> We found no one to email </h1>
  <%
    }
  %>
  </div>
</body>
</html>