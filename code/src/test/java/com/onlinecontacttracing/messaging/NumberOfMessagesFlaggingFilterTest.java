package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.storage.PositiveUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
 
@RunWith(JUnit4.class)
public final class NumberOfMessagesFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  NumberOfMessagesFlaggingFilter numberOfMessagesFlaggingFilter = new NumberOfMessagesFlaggingFilter();
  
  @Test
  public void hasSentZeroEmails() {
    assertTrue(numberOfMessagesFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void lastChanceToSendEmail() {
    for (int index = 1; index <= 100; index++) {
      user.incrementEmailsSent();
    }

    assertTrue(numberOfMessagesFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void canNoLongerSendEmail() {
    for (int index = 1; index <= 101; index++) {
      user.incrementEmailsSent();
    }
    
    assertFalse(numberOfMessagesFlaggingFilter.passesFilter(user, "hello"));
  }

}