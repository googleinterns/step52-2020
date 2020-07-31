package com.onlinecontacttracing.authentication;

/*
 * Helper class to pass along state information in URL and servlets
 */
class State {
  final String idToken;
  String userId;
  final boolean calendar;
  final boolean contacts;

  public State(String idToken, boolean calendar, boolean contacts) {
    this.idToken = idToken;
    this.calendar = calendar;
    this.contacts = contacts;
  }
}