package com.google.sps.data;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.Instant;
 
@RunWith(JUnit4.class)
public final class PositiveUserTest {

  @Test
  public void createUser() {
    PositiveUser user = new PositiveUser("Test");
    Assert.assertTrue(user.getFirstLoginInUnixTimeSeconds() == user.getLastLoginInUnixTimeSeconds());
  }

  @Test
  public void updateUserLogin() {
    PositiveUser user = new PositiveUser("Test");
    long currentTime = Instant.now().getEpochSecond();
    while (currentTime > Instant.now().getEpochSecond() - 2L) {
      //Wait two seconds
    }
    user.setLastLogin();
    Assert.assertTrue(user.getFirstLoginInUnixTimeSeconds() != user.getLastLoginInUnixTimeSeconds());

  }

  @Test
  public void emailCountInitializedToZero() {
    PositiveUser user = new PositiveUser("Test");
    Assert.assertTrue(user.getNumberOfEmailsSent() == 0);
  }

  @Test
  public void increaseEmailCount() {
    PositiveUser user = new PositiveUser("Test");
    user.incrementEmailsSent();
    Assert.assertTrue(user.getNumberOfEmailsSent() == 1);
  }

}