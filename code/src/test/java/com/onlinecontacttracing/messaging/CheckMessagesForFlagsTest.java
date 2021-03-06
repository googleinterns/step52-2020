package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.storage.PositiveUser;
import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
 
@RunWith(JUnit4.class)
public final class CheckMessagesForFlagsTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  CheckMessagesForFlags checkMessagesForFlags = new CheckMessagesForFlags();
  HtmlFlaggingFilter htmlFlaggingFilter = new HtmlFlaggingFilter();
  LinkFlaggingFilter linkFlaggingFilter = new LinkFlaggingFilter();
  ProfanityFlaggingFilter profanityFlaggingFilter = new ProfanityFlaggingFilter();
  LengthFlaggingFilter lengthFlaggingFilter = new LengthFlaggingFilter();
  CustomizeMessageTriesFlaggingFilter customizeMessageTriesFlaggingFilter = new CustomizeMessageTriesFlaggingFilter();
  
  @Test
  public void containsNoFlagsTriggered() {
    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, "");
    assertEquals(errorMessages, new ArrayList<String> ());
  }

  @Test
  public void containsOneFlag() {
    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, "<html>");
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(htmlFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }

  @Test
  public void containsTwoFlags() {
    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, "<html>   www.");
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(htmlFlaggingFilter.errorMessageToUser());
      add(linkFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }

  @Test
  public void containsThreeFlags() {
    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, "<html>   www. fuck");
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(htmlFlaggingFilter.errorMessageToUser());
      add(linkFlaggingFilter.errorMessageToUser());
      add(profanityFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }

  @Test
  public void containsFourFlags() {
    String message = "<html>   www. fuck ";
    for (int indexNumberOfAddedCharacters = 0; indexNumberOfAddedCharacters < 500; indexNumberOfAddedCharacters++) {
      message = message.concat("a");
    }
    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, message);
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(htmlFlaggingFilter.errorMessageToUser());
      add(linkFlaggingFilter.errorMessageToUser());
      add(profanityFlaggingFilter.errorMessageToUser());
      add(lengthFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }

  @Test
  public void exceededAttemptsAndFlaggedMessage() {
    String message = "<html>   www. fuck ";
    for (int indexNumberOfAddedCharacters = 0; indexNumberOfAddedCharacters < 500; indexNumberOfAddedCharacters++) {
      message = message.concat("a");
    }
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();

    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, message);
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(customizeMessageTriesFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }

  @Test
  public void exceededAttemptsAndVAlidMessage() {
    String message = "hello";
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();
    user.incrementAttemptedEmailDrafts();

    ArrayList<String> errorMessages = checkMessagesForFlags.findTriggeredFlags(user, message);
    HashSet<String> errorMessagesSet = new HashSet<String> () {{
      addAll(errorMessages);
    }};
    HashSet<String> expectedErrorMessagesSet = new HashSet<String> (){{
      add(customizeMessageTriesFlaggingFilter.errorMessageToUser());
    }};

    assertEquals(errorMessagesSet, expectedErrorMessagesSet);
  }
}