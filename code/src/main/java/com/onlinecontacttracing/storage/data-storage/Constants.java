package com.onlinecontacttracing.storage;

/**
* This class keeps track of constants we need.
*/
public class Constants {
  final static long SIX_HOURS_IN_SECONDS = 21600L;
  final static long ONE_WEEK_IN_SECONDS = 60 * 60 * 24 * 7L;
  final static long TWO_WEEKS_IN_SECONDS = ONE_WEEK_IN_SECONDS * 2;
  final static long TWO_WEEKS_IN_MILLI_SECONDS = TWO_WEEKS_IN_SECONDS * 1000;

  public final static long BUSINESS_CONTACT_COOLDOWN_SECONDS = ONE_WEEK_IN_SECONDS;

  public final static int NUMBER_OF_DRAFTS_ALLOWED = 3;
  public final static long EMAILING_THRESHOLD = 200;

  /***** Contact Tracing *****/
  // 12 feet in degrees:
  public final static long INFECTION_RADIUS_DEGREES = 330;
  // 10 minutes?
  public final static long TIME_INTERVAL_ERROR_SECONDS = 600L;

  /***** Data Persistance *****/
  public final static long NEGATIVE_USER_DATA_MAX_TIME = TWO_WEEKS_IN_SECONDS;
  public final static long POSITIVE_USER_DATA_MAX_TIME = SIX_HOURS_IN_SECONDS;
  public final static long NOTIFICATION_BATCH_MAX_TIME = ONE_WEEK_IN_SECONDS;

  public final static long SPAN_OF_TIME_TO_COLLECT_DATA = TWO_WEEKS_IN_MILLI_SECONDS;
}