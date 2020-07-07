package com.google.sps.storage;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class BusinessTest {

  @Test
  public void createBusiness() {
    Business business = new Business("Test");
    Assert.assertFalse(business.contactedInLastWeek());
  }

  @Test
  public void contactBusiness() {
    Business business = new Business("Test");
    business.updateTimeOfContact();
    Assert.assertTrue(business.contactedInLastWeek());
  }
}
