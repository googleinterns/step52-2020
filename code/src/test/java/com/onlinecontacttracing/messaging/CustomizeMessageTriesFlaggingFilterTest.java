package com.onlinecontacttracing.messaging.filters;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;
import java.util.ArrayList;

 
@RunWith(JUnit4.class)
public final class CustomizeMessageTriesFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  CustomizeMessageTriesFlaggingFilter customizeMessageTriesFlaggingFilter = new CustomizeMessageTriesFlaggingFilter();
  
  @Test
  public void hasMadeNoTries() {
    assertTrue(customizeMessageTriesFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void hasMadeThreeTries() {
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    assertTrue(customizeMessageTriesFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void hasMadeFourTries() {
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    assertFalse(customizeMessageTriesFlaggingFilter.passesFilter(user, "hello"));
  }


}