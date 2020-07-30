package com.onlinecontacttracing.messaging.filters;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;

 
@RunWith(JUnit4.class)
public final class LinkFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  LinkFlaggingFilter linkFlaggingFilter = new LinkFlaggingFilter();
  
  @Test
  public void containsNoHtmlCheck() {
    assertTrue(linkFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void containsHtmlCheck() {
    assertFalse(linkFlaggingFilter.passesFilter(user, ".us"));
  }

  @Test
  public void containsNoHtmlCheckEmptyString() {
    assertTrue(linkFlaggingFilter.passesFilter(user, ""));
  }

}