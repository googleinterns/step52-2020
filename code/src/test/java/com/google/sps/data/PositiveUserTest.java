package com.google.sps.data;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class PositiveUserTest {

  @Test
  public void createUser() {
    // Simple test for now
    PositiveUser positiveUser1 = new PositiveUser("1");
    Assert.assertTrue(positiveUser1.getFirstLogin() == positiveUser1.getLastLogin());
  }

}
