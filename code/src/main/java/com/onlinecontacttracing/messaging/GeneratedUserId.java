package com.onlinecontacttracing.messsaging;

import java.util.UUID;

/**
 * Generates a user ID for users who have not logged in in the form of anon-*
 */
public class GeneratedUserId {
  private String userId;
  private String uuidString = UUID.randomUUID().toString();

  public GeneratedUserId(String prefix) {
      userId = prefix + uuidString;
  }

  public String getIdString() {
      return userId;
  }
}