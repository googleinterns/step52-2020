package com.google.cloud.authentication.serviceaccount;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.IamScopes;
import com.google.api.services.iam.v1.model.CreateServiceAccountKeyRequest;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class CreateServiceAccountKey {

  // Creates a key for a service account.
  public static void createKey(String projectId) {
    // String projectId = "my-project-id";

    Iam service = null;
    try {
      service = initService();
    } catch (IOException | GeneralSecurityException e) {
      System.out.println("Unable to initialize service: \n" + e.toString());
      return;
    }

    try {
      ServiceAccountKey key =
          service
              .projects()
              .serviceAccounts()
              .keys()
              .create(
                  "projects/-/serviceAccounts/your-service-account-name@"
                      + projectId
                      + ".iam.gserviceaccount.com",
                  new CreateServiceAccountKeyRequest())
              .execute();

      System.out.println("Created key: " + key.getName());
    } catch (IOException e) {
      System.out.println("Unable to create service account key: \n" + e.toString());
    }
  }

  private static Iam initService() throws GeneralSecurityException, IOException {
    // Use the Application Default Credentials strategy for authentication. For more info, see:
    // https://cloud.google.com/docs/authentication/production#finding_credentials_automatically
    GoogleCredentials credential =
        GoogleCredentials.getApplicationDefault()
            .createScoped(Collections.singleton(IamScopes.CLOUD_PLATFORM));
    // Initialize the IAM service, which can be used to send requests to the IAM API.
    Iam service =
        new Iam.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credential))
            .setApplicationName("service-account-keys")
            .build();
    return service;
  }
}