package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class CalendarDataForNegativeUser implements Runnable {
  Credential credential;

  public CalendarDataForNegativeUser(Credential credential) {
    this.credential = credential;
  }

  public void run() {
    // Get contacts from people api
  }
}