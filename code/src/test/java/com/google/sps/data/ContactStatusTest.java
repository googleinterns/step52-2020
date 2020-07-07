package com.google.sps.data;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class ContactStatusTest {

  @Test
  public void testMethodsForCheckingBusinessStatus() {
    ContactStatus business = new BusinessNumber("Test", 9703456789L);
    Assert.assertFalse(business.getStatus());
    business.setSuccess();
    Assert.assertTrue(business.getStatus());
  }

  @Test
  public void testMethodsForCheckingPersonStatus() {
    PersonEmail person = new PersonEmail("Test", "test@google.com");
    Assert.assertFalse(person.getStatus());
    person.setSuccess();
    Assert.assertTrue(person.getStatus());
  }

}
