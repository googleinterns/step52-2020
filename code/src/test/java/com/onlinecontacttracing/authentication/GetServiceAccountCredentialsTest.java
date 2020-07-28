package com.onlinecontacttracing.storage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.cloud.auth.samples.GetServiceAccountCredentials;
 
@RunWith(JUnit4.class)
public final class GetServiceAccountCredentialsTest {

  @Test
  public void CheckCreationOfCredentials() {
    GetServiceAccountCredentials.authImplicit();
    assertTrue(true);
  }


}