
package com.google.cloud.authentication.serviceaccount;

import com.google.api.gax.paging.Page;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;

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

/**
 * Demonstrate various ways to authenticate requests using Cloud Storage as an example call.
 */
public class GetServiceAccountCredentials {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String serviceAccountId = "online-contact-tracing@appspot.gserviceaccount.com";
    private static final String CREDENTIALS_FILE_PATH = "online-contact-tracing-d231b0b3bf47.p12";

  public static GoogleCredential getServiceAccountCredentials() {
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      InputStream in = (new GetServiceAccountCredentials()).getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);
      System.out.println(in);
      GoogleCredential credential = (new GoogleCredential.Builder())
      .setTransport(HTTP_TRANSPORT)
      .setJsonFactory(JSON_FACTORY)
      .setServiceAccountId(serviceAccountId)
      .setServiceAccountScopes(Collections.singleton(GmailScopes.GMAIL_SEND))
      .setServiceAccountPrivateKey(getServiceAccountPrivateKeyFromP12File2(in))
      .setServiceAccountUser(serviceAccountId)
      .build();

    return credential;
    } catch (Exception e) {
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