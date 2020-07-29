
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
import javax.servlet;


/**
 * Demonstrate various ways to authenticate requests using Cloud Storage as an example call.
 */
public class GetServiceAccountCredentials {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String serviceAccountId = "online-contact-tracing-email@covid-catchers-fixed-gcp.iam.gserviceaccount.com";
    private static final String CREDENTIALS_FILE_PATH = "WEB-INF/covid-catchers-fixed-gcp-4270ee645eb8.p12";

  public static void authImplicit() {
    File f = new File(CREDENTIALS_FILE_PATH);
    File f1 = new File("/code/test.txt");
    System.out.println(f1.exists());
    System.out.println(f1.getName());
      // Get the absolute path of file f 
      String absolute = f.getAbsolutePath(); 
      String absoluteDiskPath = servletContext.getRealPath(CREDENTIALS_FILE_PATH);
      // Display the file path of the file object 
      // and also the file path of absolute file 
      System.out.println("Original path: " + f.getPath()); 
      System.out.println("Absolute path: " + absolute); 
      System.out.println("Absolute disk path: " + absoluteDiskPath); 
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    //   Credential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)    
    // .setJsonFactory(JSON_FACTORY)
    // .setServiceAccountId(serviceAccountId)
    // .setServiceAccountPrivateKeyFromP12File(new File("/covid-catchers-fixed-gcp-4270ee645eb8.p12"))
    // .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_LABELS))
    // .build();

    GoogleCredential credential = new GoogleCredential.Builder()
    .setTransport(HTTP_TRANSPORT)
    .setJsonFactory(JSON_FACTORY)
    .setServiceAccountId(serviceAccountId)
    .setServiceAccountPrivateKeyFromP12File(new File(CREDENTIALS_FILE_PATH))
    .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_SEND))
    .setServiceAccountUser(serviceAccountId)
    .build();
    System.out.println("YAYYYYYYY");
    } catch (Exception e) {
      System.out.println("yikes yikes");
      // System.out.println(e);
      e.printStackTrace();
    }

  }


}