package com.onlinecontacttracing.storage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
@RunWith(JUnit4.class)
public final class ContactStatusTest {

  @Test
  public void falseBusinessStatus() {
    ContactStatus business = new BusinessNumber("Test", "9703456789");

    assertFalse(business.hasBeenContactedSuccessfully());
  }

  @Test
  public void trueBusinessStatus() {
    ContactStatus business = new BusinessNumber("Test", "9703456789");

    business.markContactedSuccessfully();

    assertTrue(business.hasBeenContactedSuccessfully());
  }

  @Test
  public void falsePersonStatus() {
    PersonEmail person = new PersonEmail("test@google.com", "SP", "VERSION_1", "EN", "NEW_YORK", "EN", "VERSION_1");

    assertFalse(person.hasBeenContactedSuccessfully());
  }

  @Test
  public void truePersonStatus() {
    PersonEmail person = new PersonEmail("test@google.com", "EN", "VERSION_1", "EN", "NEW_YORK", "EN", "VERSION_1");

    person.markContactedSuccessfully();

    assertTrue(person.hasBeenContactedSuccessfully());
  }

}