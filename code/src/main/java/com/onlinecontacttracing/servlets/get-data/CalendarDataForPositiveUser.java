package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class CalendarDataForPositiveUser implements Runnable {
  Credential credential;

  public CalendarDataForPositiveUser(Credential credential) {
    this.credential = credential;
  }

  public void run() {
    // Get contacts from people api
  }
}