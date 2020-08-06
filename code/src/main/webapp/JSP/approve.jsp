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
com.onlinecontacttracing.storage.PositiveUserContacts,
com.onlinecontacttracing.storage.PotentialContact,
com.onlinecontacttracing.storage.PositiveUserPlaces,
com.onlinecontacttracing.storage.Place,
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
    // Parse state parameter from Json string to AuthorizationRoundTripState class
    Gson gson = new Gson();
    String authorizationRoundTripStateAsJson = request.getParameter("authState");
    AuthorizationRoundTripState authorizationRoundTripState = gson.fromJson(authorizationRoundTripStateAsJson, AuthorizationRoundTripState.class);

    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    // Make verifier to get payload
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
    .setAudience(Collections.singletonList("83357506440-etvnksinbmnpj8eji6dk5ss0tbk9fq4g.apps.googleusercontent.com"))
    .build();
    GoogleIdToken idToken = verifier.verify(authorizationRoundTripState.getIdToken());
    Payload payload = idToken.getPayload();
    String userId = payload.getSubject();
    PositiveUserContacts contacts = ofy().load().type(PositiveUserContacts.class).id(userId).now();
    boolean dataToDisplayExists = false;
    if (contacts != null && !contacts.getListOfContacts().isEmpty()) {
        dataToDisplayExists = true;
  %>
      <p class="mission-statement"> Here are the contacts we found. Please choose anyone you may have come in contact with so that we can email them: </p>
      <div class="picker header">
        <p> Name </p>
        <p class="email"> Email </p>
        <label class="container"> Contact Y/N </label>
      </div>
      <div class="list">
  <% 
      for (PotentialContact contact : contacts.getListOfContacts()) {
        String name = Optional.ofNullable(contact.getName()).orElse("No name found");
  %>
      <div class="picker contact">
        <p> <%=  name%> </p>
        <p class="email"> <%= contact.getEmail() %> </p>
        <label class="container">
          <input type="checkbox">
          <span class="checkmark"></span>
        </label>
        <select class="language">
          <option value="EN">English</option>
          <option value="SP">Spanish</option>
        </select>
      </div>
  <%
      }
  %>
      </div>
  <%
    }

    PositiveUserPlaces places = ofy().load().type(PositiveUserPlaces.class).id(userId).now();
    if (places != null && places.getListOfPlaces() != null) {
        dataToDisplayExists = true;
  %>
      <p class="mission-statement"> Please confirm the places you have been to so that we can call them: </p>
      <div class="picker header">
        <p> Place </p>
        <p> Time </p>
        <label class="container"> Contact Y/N </label>
      </div>
      <div class="list">
  <%
      for (Place place : places.getListOfPlaces()) {
  %>
      <div class="picker place">
        <p> <%= place.getName() %> </p>
        <p> <%= place.displayTimeIntervalAsDate(authorizationRoundTripState.getZoneOffset())%></p>
        <label class="container">
          <input type="checkbox">
          <span class="checkmark"></span>
        </label>
      </div>
  <%
      }
  %>
      </div>
  <%
    }

    if (dataToDisplayExists) {
  %>
    <div id="blue-buttons-to-procede">
      <button id="login-button-left-or-top" onclick="sendListToServlet()"> Submit </button>
    </div>
  <%
    } else {
  %>
    <h1> We do not have any data </h1>
  <%
    }
  %>
  </div>
</body>
</html>