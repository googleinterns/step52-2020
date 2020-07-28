package com.onlinecontacttracing.messsaging;

import java.util.UUID;

/**
 * Generates a user ID for users who have not logged in in the form of anon-*
 */
public class GeneratedUserId {
  private String userId;
  private UUID uuid = new UUID(123456789, 987654321);
  private String randomized = uuid.randomUUID().toString();

  public GeneratedUserId(String prefix) {
      userId = prefix + randomized;
  }

  public String getIdString() {
      return userId;
  }
}