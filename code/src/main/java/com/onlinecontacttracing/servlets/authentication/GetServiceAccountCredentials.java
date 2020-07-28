

package com.google.cloud.auth.samples;

import com.google.api.gax.paging.Page;
// import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;


/**
 * Demonstrate various ways to authenticate requests using Cloud Storage as an example call.
 */
public class GetServiceAccountCredentials {

  public static void authImplicit() {
    System.out.println("HELLLOOOOOO");
    // If you don't specify credentials when constructing the client, the client library will
    // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
    String jsonPath = "WEB-INF/secret-key.json";
    try{
    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(new File(jsonPath)))
        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    System.out.println(credentials);
    
    } catch (Exception e) {
      System.out.println("oh boy");
    }

  }


}