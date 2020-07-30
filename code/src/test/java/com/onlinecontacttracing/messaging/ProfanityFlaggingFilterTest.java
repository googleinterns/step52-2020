package com.onlinecontacttracing.messaging.filters;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;

 
@RunWith(JUnit4.class)
public final class ProfanityFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  ProfanityFlaggingFilter profanityFlaggingFilter = new ProfanityFlaggingFilter ();
  @Test
  public void containsNoProfanityCheck() {
    assertTrue(profanityFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void containsProfanityCheck() {
    assertFalse(profanityFlaggingFilter.passesFilter(user, "fuck"));
  }

  @Test
  public void containsNoProfanityCheckEmptyString() {
    assertTrue(profanityFlaggingFilter.passesFilter(user, ""));
  }

}