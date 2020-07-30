package com.onlinecontacttracing.messaging.filters;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.onlinecontacttracing.storage.PositiveUser;

 
@RunWith(JUnit4.class)
public final class HtmlFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  HtmlFlaggingFilter htmlFlaggingFilter = new HtmlFlaggingFilter ();
  
  @Test
  public void containsNoHtmlCheck() {
    assertTrue(htmlFlaggingFilter.passesFilter(user, "hello"));
  }

  @Test
  public void containsHtmlCheck() {
    assertFalse(htmlFlaggingFilter.passesFilter(user, "<html>"));
  }

  @Test
  public void containsNoHtmlCheckEmptyString() {
    assertTrue(htmlFlaggingFilter.passesFilter(user, ""));
  }

}