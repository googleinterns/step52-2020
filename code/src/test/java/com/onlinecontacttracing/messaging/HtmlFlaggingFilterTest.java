package com.onlinecontacttracing.messaging.filters;

import com.onlinecontacttracing.storage.PositiveUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
 
@RunWith(JUnit4.class)
public final class HtmlFlaggingFilterTest {
  PositiveUser user = new PositiveUser("Test", "test@google.com");
  HtmlFlaggingFilter htmlFlaggingFilter = new HtmlFlaggingFilter();
  
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