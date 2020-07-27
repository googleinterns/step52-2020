package com.onlinecontacttracing.messsaging;

import java.util.UUID;

/**
 * Generates a user ID for users who have not logged in in the form of anon-*
 */
public class GeneratedUserId {
  private static String userId;
  private static UUID uuid = new UUID(123456789, 987654321);
  private static String randomized = uuid.randomUUID().toString();

  public GeneratedUserId(String prefix) {
      userId = prefix + randomized;
  }

  public static String getIdString() {
      return userId;
  }
}