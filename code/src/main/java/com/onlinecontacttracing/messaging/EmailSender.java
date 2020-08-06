package com.onlinecontacttracing.messaging;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import java.util.logging.Logger;
import com.onlinecontacttracing.storage.PersonEmail;
import com.onlinecontacttracing.storage.NotificationBatch;
import org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * The email sender will load a list of emails from the notification batch
 * and compile messages to send to each person. They will receive an email
 * from cccoders@onlinecontacttracing.com.
 */
public class EmailSender {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static final String SERVICE_ACCOUNT_ID = "online-contact-tracing@appspot.gserviceaccount.com";
  private static final String EMAIL_TO_SEND_WITH = "cccoders@onlinecontacttracing.com";
  private static final String CREDENTIALS_FILE_PATH = "online-contact-tracing-f798898872f4.p12";
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final String APPLICATION_NAME = "Online Contact Tracing";
  static final Logger log = Logger.getLogger(EmailSender.class.getName());


  // public static void sendEmailsOut(String emailSubject, CompiledMessage compiledMessage, String systemMessageLanguage, String localityResourceLanguage) {

  /**
  * Sends emails with a email subject and a message to a list of contacts.
  */
  public static void sendEmailsOut(EmailSubject emailSubject, CompiledMessage compiledMessage, String systemMessageLanguage, String localityResourceLanguage) {


    try{
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

      InputStream in = (new EmailSender()).getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);

      GoogleCredential serviceAccountCredential = new GoogleCredential.Builder()
        .setTransport(HTTP_TRANSPORT)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_ID)
        .setServiceAccountUser(EMAIL_TO_SEND_WITH)
        .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_SEND))
        .setServiceAccountPrivateKeyFromP12File(in)
        .build();

      Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, serviceAccountCredential)
                  .setApplicationName(APPLICATION_NAME)
                  .build();
                  

      String emailBody = compiledMessage.compileMessages(systemMessageLanguage, localityResourceLanguage);
      // String emailBody = compiledMessage.getCompiledBackendMessage();

      // String emailBody = compiledMessage.compileMessages(messageLanguage);

      NotificationBatch notificationInfo = ofy().load().type(NotificationBatch.class).id(compiledMessage.getUserId()).now();

      for (PersonEmail contact : notificationInfo.getPersonEmails()) {
        sendMessage(contact.getEmail(), emailSubject.getTranslation("EN"), emailBody, service);
        contact.markContactedSuccessfully();
      }
                  
    } catch (MessagingException e) {
        log.warning("error in sending emails out" + e.toString());
    } catch (GeneralSecurityException e) {
      log.warning("http transport failed, security error" + e.toString());
    } catch (Exception e) {
      log.warning("general exception" + e.toString());
    }
  }

  /**
  * Sends out messages to a specific contact.
  */
  private static void sendMessage(String to, String emailSubject, String emailContent, Gmail service) throws MessagingException, IOException {
    // Set up MimeMessage
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage email = new MimeMessage(session);

    // Define MimeMessage entries
    email.setFrom(new InternetAddress(EMAIL_TO_SEND_WITH));
    email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
    email.setSubject(emailSubject);
    email.setText(emailContent);

    // Convert MimeMessage to Message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);

    // Send Message
    message = service.users().messages().send(EMAIL_TO_SEND_WITH, message).execute();
  }
}