package com.google.sps.storage;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class ContactStatusTest {

  @Test
  public void falseBusinessStatus() {
    ContactStatus business = new BusinessNumber("Test", "9703456789");
    Assert.assertFalse(business.getStatus());
  }

  @Test
  public void trueBusinessStatus() {
    ContactStatus business = new BusinessNumber("Test", "9703456789");
    business.setSuccess();
    Assert.assertTrue(business.getStatus());
  }

  @Test
  public void falsePersonStatus() {
    PersonEmail person = new PersonEmail("Test", "test@google.com");
    Assert.assertFalse(person.getStatus());
  }

  @Test
  public void truePersonStatus() {
    PersonEmail person = new PersonEmail("Test", "test@google.com");
    person.setSuccess();
    Assert.assertTrue(person.getStatus());
  }

}
