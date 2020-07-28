package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class CalendarDataForNegativeUser implements Runnable {
  private final Credential credential;

  public CalendarDataForNegativeUser(Credential credential) {
    this.credential = credential;
  }

  public void run() {
    // TODO Get contacts from people api
  }
}