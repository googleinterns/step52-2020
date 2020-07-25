package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class GetCalendarDataForPositiveUser implements Runnable {
  Credential credential;

  public GetCalendarDataForPositiveUser(Credential credential) {
    this.credential = credential;
  }

  public void run() {
    // Get contacts from people api
  }
}