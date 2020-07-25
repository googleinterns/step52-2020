package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class GetCalendarDataForNegativeUser implements Runnable {
  Credential credential;

  public GetCalendarDataForNegativeUser(Credential credential) {
    this.credential = credential;
  }

  public void run() {
    // Get contacts from people api
  }
}