
package com.google.cloud.auth.samples;

import com.google.api.gax.paging.Page;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.api.client.json.JsonFactory;
import java.util.Collections;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import java.io.File;
import com.google.api.client.auth.oauth2.Credential;

import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.security.GeneralSecurityException;
import com.google.api.client.util.SecurityUtils;
import java.security.PrivateKey;
// import javax.servlet.ServletContext;
// import javax.servlet.http.HttpSession;
// import javax.servlet;


/**
 * Demonstrate various ways to authenticate requests using Cloud Storage as an example call.
 */
public class GetServiceAccountCredentials {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String serviceAccountId = "online-contact-tracing-email@covid-catchers-fixed-gcp.iam.gserviceaccount.com";
    private static final String CREDENTIALS_FILE_PATH = "WEB-INF/covid-catchers-fixed-gcp-4270ee645eb8.p12";
    private static final String CREDENTIALS_FILE_PATH2 = "WEB-INF/covid-catchers-fixed-gcp-cb8d9192a84a.json";

  public static GoogleCredential getServiceAccountCredentials() {
    File f = new File(CREDENTIALS_FILE_PATH);
    File f1 = new File("WEB-INF");
    System.out.println(f1.exists());
    System.out.println(f1.getName());
      // Get the absolute path of file f 
      String absolute = f1.getAbsolutePath(); 
      // String absoluteDiskPath = servletContext.getRealPath(CREDENTIALS_FILE_PATH);
      // Display the file path of the file object 
      // and also the file path of absolute file 
      System.out.println("Original path: " + f.getPath()); 
      System.out.println("Absolute path: " + absolute); 
      
      // System.out.println("Absolute disk path: " + absoluteDiskPath); 
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      // ServletContext s = getServletContext();
    // InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH2));
    // InputStream in = Thread.currentThread().getContextClassLoader()
    // .getResourceAsStream(CREDENTIALS_FILE_PATH);
    InputStream in = (new GetServiceAccountCredentials()).getClass().getClassLoader().getResourceAsStream("covid-catchers-fixed-gcp-4270ee645eb8.p12");
    // InputStream in = GetServiceAccountCredentials.class.getClassLoader().getResourceAsStream("covid-catchers-fixed-gcp-4270ee645eb8.p12");
  //  InputStream in = GetServiceAccountCredentials.class.getResourceAsStream("covid-catchers-fixed-gcp-4270ee645eb8.p12");
      // InputStream in = s.getResourceAsStream("/WEB-INF/covid-catchers-fixed-gcp-4270ee645eb8.p12");
    System.out.println(in);
    
    
    GoogleCredential credential = (new GoogleCredential.Builder())
    .setTransport(HTTP_TRANSPORT)
    .setJsonFactory(JSON_FACTORY)
    .setServiceAccountId(serviceAccountId)
    .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_SEND))
    .setServiceAccountPrivateKey(getServiceAccountPrivateKeyFromP12File2(in))
    .setServiceAccountUser(serviceAccountId)
    .build();

    
    System.out.println("YAYYYYYYY");
    System.out.println(credential);
    return credential;
    } catch (Exception e) {
      System.out.println("yikes yikes");
      // System.out.println(e);
      e.printStackTrace();
      return null;//not sure what to return here
    }

  }

  public static PrivateKey getServiceAccountPrivateKeyFromP12File2(InputStream p12File)
    throws GeneralSecurityException, IOException {
    PrivateKey serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(
    SecurityUtils.getPkcs12KeyStore(), p12File, "notasecret",
    "privatekey", "notasecret");
    return serviceAccountPrivateKey;
  }


}