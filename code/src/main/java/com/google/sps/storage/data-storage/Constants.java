package com.google.sps.storage;

/**
* This class keeps track of constants we need.
*/
public class Constants {
  final static long BUSINESS_CONTACT_COOLDOWN_SECONDS = 604800L;
  final static long EMAILING_THRESHOLD = 200;
  final static long INFECTION_RADIUS = 12; // TODO: we will need to convert this value from feet to degrees
  final static long TIME_INTERVAL_ERROR_SECONDS = 600L; // 10 minutes?
}