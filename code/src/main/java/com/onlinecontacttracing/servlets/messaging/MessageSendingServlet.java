package com.onlinecontacttracing.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import com.onlinecontacttracing.authentication.CheckForApiAuthorizationServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.onlinecontacttracing.storage.PositiveUser;

import com.googlecode.objectify.Objectify; 
import com.googlecode.objectify.ObjectifyFactory; 
import com.googlecode.objectify.ObjectifyService; 
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.onlinecontacttracing.storage.PotentialContact;
import com.onlinecontacttracing.messaging.EmailSender;
import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.messaging.CompiledMessage;
import com.onlinecontacttracing.messaging.SystemMessage;
import com.onlinecontacttracing.messaging.LocalityResource;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;
import com.google.cloud.authentication.serviceaccount.CreateServiceAccountKey;


@WebServlet("/send-messages")
public class MessageSendingServlet extends HttpServlet {

  static final Logger log = Logger.getLogger(DeleteNegativeUserLocationsServlet.class.getName());

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    String systemMessageName = request.getParameter("systemMessage");
    String localityResourceName = request.getParameter("localityResource");
    String messageLanguage = request.getParameter("messageLanguage");

    // CreateServiceAccountKey.createKey("covid-catchers-fixed-gcp");
    SystemMessage systemMessage = SystemMessage.getSystemMessageFromString(systemMessageName);
    LocalityResource localityResource = LocalityResource.getLocalityResourceFromString(localityResourceName);

    try {
        GoogleAuthorizationCodeFlow flow = CheckForApiAuthorizationServlet.getFlow();
        String userId = CheckForApiAuthorizationServlet.getPayload(idToken, flow).getSubject();

        ofy().save().entity(new PositiveUser(userId, "cynthiama@google.com")).now();
        ofy().save().entity(new PositiveUserContacts(userId)).now();
        ofy().save().entity(new CustomizableMessage(userId, "hi cynthia!!!")).now();

        PositiveUser positiveUser = ofy().load().type(PositiveUser.class).id(userId).now();
        PositiveUserContacts positiveUserContacts = ofy().load().type(PositiveUserContacts.class).id(userId).now();
        CustomizableMessage customizableMessage = ofy().load().type(CustomizableMessage.class).id(userId).now();

        positiveUserContacts.add("cynthia ma", "cynthiama@google.com");
        positiveUserContacts.add("nico", "nvergel@google.com");

        CompiledMessage compiledMessage = new CompiledMessage(systemMessage, localityResource, customizableMessage, positiveUser);//fix the enum resources
        EmailSender emailSender = new EmailSender("COVID-19 Updates", positiveUserContacts.getListOfContacts(), compiledMessage);
        emailSender.sendEmailsOut(messageLanguage);
        // response.getWriter().println(compiledMessage.getCompiledFrontendDisplayMessage());
    } catch(Exception e) {
        e.printStackTrace();
    }
  }
}