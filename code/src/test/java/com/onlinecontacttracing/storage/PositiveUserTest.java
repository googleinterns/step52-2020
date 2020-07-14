package com.onlinecontacttracing.storage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.Instant;
 
@RunWith(JUnit4.class)
public final class PositiveUserTest {

  @Test
  public void createUser() {
    PositiveUser user = new PositiveUser("Test", "test@google.com");

    assertTrue(user.getFirstLoginInSeconds() == user.getLastLoginInSeconds());
  }

  @Test
  public void updateUserLogin() {
    PositiveUser user = new PositiveUser("Test", "test@google.com");
    long currentTime = Instant.now().getEpochSecond();

    while (currentTime > Instant.now().getEpochSecond() - 2L) {
      //Wait two seconds
    }
    user.setLastLogin();

    assertTrue(user.getFirstLoginInSeconds() != user.getLastLoginInSeconds());

  }

  @Test
  public void emailCountInitializedToZero() {
    PositiveUser user = new PositiveUser("Test", "test@google.com");

    assertTrue(user.getNumberOfEmailsSent() == 0);
    assertTrue(user.userCanStillSendEmails());
  }

  @Test
  public void increaseEmailCount() {
    PositiveUser user = new PositiveUser("Test", "test@google.com");

    user.incrementEmailsSent();

    assertTrue(user.getNumberOfEmailsSent() == 1);
    assertTrue(user.userCanStillSendEmails());
  }

  @Test
  public void emailThresholdReached() {
    PositiveUser user = new PositiveUser("Test", "test@google.com");

    for (int i = 0; i < Constants.EMAILING_THRESHOLD; i++) 
      user.incrementEmailsSent();
    
    assertFalse(user.userCanStillSendEmails());
  }

}