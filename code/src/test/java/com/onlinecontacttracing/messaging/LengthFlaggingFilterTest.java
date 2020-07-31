package com.onlinecontacttracing.messaging.filters;

<<<<<<< HEAD
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;

=======
import com.onlinecontacttracing.storage.PositiveUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
>>>>>>> master
 
@RunWith(JUnit4.class)
public final class LengthFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
<<<<<<< HEAD
  LengthFlaggingFilter lengthFlaggingFilter = new LengthFlaggingFilter ();
=======
  LengthFlaggingFilter lengthFlaggingFilter = new LengthFlaggingFilter();
>>>>>>> master
  
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
<<<<<<< HEAD
=======

>>>>>>> master
    assertTrue(lengthFlaggingFilter.passesFilter(user, message));
  }

  @Test
  public void messageIsAtCharacterLimit() {
    String message = "";
    for (int numberOfCharacters = 0; numberOfCharacters < 500; numberOfCharacters++) {
      message = message.concat("a");
    }
<<<<<<< HEAD
=======

>>>>>>> master
    assertTrue(lengthFlaggingFilter.passesFilter(user, message));
  }

  @Test
  public void messageIsJustOverCharacterLimit() {
    String message = "";
    for (int numberOfCharacters = 0; numberOfCharacters < 501; numberOfCharacters++) {
      message = message.concat("a");
    }
<<<<<<< HEAD
=======

>>>>>>> master
    assertFalse(lengthFlaggingFilter.passesFilter(user, message));
  }

}