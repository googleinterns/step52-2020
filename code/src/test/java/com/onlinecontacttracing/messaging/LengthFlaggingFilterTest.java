package com.onlinecontacttracing.messaging.filters;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;

 
@RunWith(JUnit4.class)
public final class LengthFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  LengthFlaggingFilter lengthFlaggingFilter = new LengthFlaggingFilter ();
  
  @Test
  public void emptyStringPasses() {
    assertTrue(lengthFlaggingFilter.passesFilter(user, ""));
  }

  @Test
  public void messageAlmostExceedsLimit() {
    String message = "";
    for (int numberOfCharacters = 0; numberOfCharacters < 499; numberOfCharacters++) {
      message = message.concat("a");
    }
    assertTrue(lengthFlaggingFilter.passesFilter(user, message));
  }

  @Test
  public void messageIsAtCharacterLimit() {
    String message = "";
    for (int numberOfCharacters = 0; numberOfCharacters < 500; numberOfCharacters++) {
      message = message.concat("a");
    }
    assertTrue(lengthFlaggingFilter.passesFilter(user, message));
  }

  @Test
  public void messageIsJustOverCharacterLimit() {
    String message = "";
    for (int numberOfCharacters = 0; numberOfCharacters < 501; numberOfCharacters++) {
      message = message.concat("a");
    }
    assertFalse(lengthFlaggingFilter.passesFilter(user, message));
  }

}