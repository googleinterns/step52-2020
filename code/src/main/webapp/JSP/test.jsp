<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.google.api.client.googleapis.javanet.GoogleNetHttpTransport,
com.google.api.client.http.javanet.NetHttpTransport,
com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier,
java.util.Collections,
com.google.api.client.json.JsonFactory,
com.google.api.client.json.jackson2.JacksonFactory,
com.google.api.client.googleapis.auth.oauth2.GoogleIdToken,
com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload,
static com.googlecode.objectify.ObjectifyService.ofy,
com.onlinecontacttracing.storage.PositiveUserContacts,
com.onlinecontacttracing.storage.PotentialContact,
com.onlinecontacttracing.storage.PositiveUserPlaces,
com.onlinecontacttracing.storage.Place" %>
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
    String idTokenString = request.getParameter("idToken");
    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    // Make verifier to get payload
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
    .setAudience(Collections.singletonList("1080865471187-u1vse3ccv9te949244t9rngma01r226m.apps.googleusercontent.com"))
    .build();
    GoogleIdToken idToken = verifier.verify(idTokenString);
    Payload payload = idToken.getPayload();
    String userId = payload.getSubject();
    PositiveUserContacts contacts = ofy().load().type(PositiveUserContacts.class).id(userId).now();
    if (contacts != null) {
  %>
      <p class="mission-statement"> Here are the contacts we found. Please choose anyone you may have come in contact with: </p>
      <div class="picker">
        <p> Name </p>
        <p class="email"> Email </p>
      </div>
  <% 
      for (PotentialContact contact : contacts.getListOfContacts()) {
  %>
      <div class="picker contact">
        <input type="checkbox" checked>
        <p> <%= contact.getName() %> </p>
        <p class="email"> <%= contact.getEmail() %> </p>
      </div>
  <%
      }
    }

    PositiveUserPlaces places = ofy().load().type(PositiveUserPlaces.class).id(userId).now();
    if (places != null) {
  %>
      <p class="mission-statement"> Here are the places you have been to. Please confirm: </p>
  <%
      for (Place place : places.getListOfPlaces()) {
  %>
      <div class="picker place">
        <input type="checkbox" checked>
        <p> <%= place.getName() %> on <%= new java.util.Date(place.getIntervalStartSeconds() * 1000)%> </p>
      </div>
  <%
      }
    }
  %>
    <div id="blue-buttons-to-procede">
      <button id="login-button-left-or-top" onclick="suPeRCooLFuNcTiONGoEsHerE()"> Submit </button>
    </div>
  </div>
</body>
</html>