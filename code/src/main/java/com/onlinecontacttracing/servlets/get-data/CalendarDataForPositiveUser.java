package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class CalendarDataForPositiveUser implements Runnable {
  private final Credential credential;

  public CalendarDataForPositiveUser(Credential credential) {
    this.credential = credential;
  }

  @Override
  public void run() {
    // TODO Get contacts from people api
  }
}