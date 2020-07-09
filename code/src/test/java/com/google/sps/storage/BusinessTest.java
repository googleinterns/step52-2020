package com.google.sps.storage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class BusinessTest {

  @Test
  public void createBusiness() {
    Business business = new Business("Test");

    assertFalse(business.stillInCooldownPeriodFromLastContact());
  }

  @Test
  public void contactBusiness() {
    Business business = new Business("Test");

    business.updateTimeOfContact();

    assertTrue(business.stillInCooldownPeriodFromLastContact());
  }
}