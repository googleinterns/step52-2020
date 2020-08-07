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
   <link rel="shortcut icon" type="image/svg" href="image/favicon.svg"/>
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
      <div class="picker header contact" style="width:100%">
        <table id="contactsTable" style="width:100%">
          <tr>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Check to Send</th>
            <th>System Message Language</th>
            <th>System Message Version</th>
            <th>Locality Resource Language</th>
            <th>Locality Resource Version</th>
            <th>Email Subject Language</th>
            <th>Email Subject Version</th>
          </tr>
  <% 
      for (PotentialContact contact : contacts.getListOfContacts()) {
        String name = Optional.ofNullable(contact.getName()).orElse("No name found");
  %>
          <tr>
            <th><%=  name%></th>
            <th><%= contact.getEmail() %></th>
            <th>
                <input type="checkbox">
            </th>
            <th>
              <select class="systemMessageLanguage">
                <option value="EN">English</option>
                <option value="SP">Spanish</option>
              </select>
            </th>
            <th>
              <select class="systemMessageVersion">
                <option value="VERSION_1">Version 1</option>
              </select>
            </th>
            <th>
              <select class="localityResourceLanguage">
                <option value="EN">English</option>
                <option value="SP">Spanish</option>
              </select>
            </th>
            <th>
              <select class="localityResourceVersion">
                <option value="US">US</option> 
                <option value="ALABAMA">ALABAMA</option> 
                <option value="ALASKA">ALASKA</option> 
                <option value="ARIZONA">ARIZONA</option> 
                <option value="ARKANSAS">ARKANSAS</option> 
                <option value="CALIFORNIA">CALIFORNIA</option> 
                <option value="COLORADO">COLORADO</option> 
                <option value="CONNECTICUT">CONNECTICUT</option> 
                <option value="DELAWARE">DELAWARE</option> 
                <option value="FLORIDA">FLORIDA</option> 
                <option value="GEORGIA">GEORGIA</option> 
                <option value="HAWAII">HAWAII</option> 
                <option value="IDAHO">IDAHO</option> 
                <option value="ILLINOIS">ILLINOIS</option> 
                <option value="INDIANA">INDIANA</option> 
                <option value="IOWA">IOWA</option> 
                <option value="KANSAS">KANSAS</option> 
                <option value="KENTUCKY">KENTUCKY</option> 
                <option value="LOUISIANA">LOUISIANA</option> 
                <option value="MAINE">MAINE</option> 
                <option value="MARYLAND">MARYLAND</option> 
                <option value="MASSACHUSETTS">MASSACHUSETTS</option> 
                <option value="MICHIGAN">MICHIGAN</option> 
                <option value="MINNESOTA">MINNESOTA</option> 
                <option value="MISSISSIPPI">MISSISSIPPI</option> 
                <option value="MISSOURI">MISSOURI</option> 
                <option value="MONTANA">MONTANA</option> 
                <option value="NEBRASKA">NEBRASKA</option> 
                <option value="NEVADA">NEVADA</option> 
                <option value="NEW_HAMPSHIRE">NEW HAMPSHIRE</option> 
                <option value="NEW_JERSEY">NEW JERSEY</option> 
                <option value="NEW_MEXICO">NEW MEXICO</option> 
                <option value="NEW_YORK">NEW YORK</option> 
                <option value="NORTH_CAROLINA">NORTH CAROLINA</option> 
                <option value="NORTH_DAKOTA">NORTH DAKOTA</option> 
                <option value="OHIO">OHIO</option> 
                <option value="OKLAHOMA">OKLAHOMA</option> 
                <option value="OREGON">OREGON</option> 
                <option value="PENNSYLVANIA">PENNSYLVANIA</option> 
                <option value="RHODE_ISLAND">RHODE ISLAND</option> 
                <option value="SOUTH_CAROLINA">SOUTH CAROLINA</option>
                <option value="SOUTH_DAKOTA">SOUTH DAKOTA</option> 
                <option value="TENNESSEE">TENNESSEE</option> 
                <option value="TEXAS">TEXAS</option> 
                <option value="UTAH">UTAH</option> 
                <option value="VERMONT">VERMONT</option> 
                <option value="VIRGINIA">VIRGINIA</option> 
                <option value="WASHINGTON">WASHINGTON</option> 
                <option value="WEST_VIRGINIA">WEST VIRGINIA</option> 
                <option value="WISCONSIN">WISCONSIN</option> 
                <option value="WYOMING">WYOMING</option> 
              </select>
            </th>
            <th>
              <select class="emailSubjectLanguage">
                <option value="EN">English</option>
                <option value="SP">Spanish</option>
              </select>
            </th>
            <th>
              <select class="emailSubjectVersion">
                <option value="VERSION_1">Version 1</option>
              </select>
            </th>
          </tr>
  <%
      }
  %>
      </table>
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