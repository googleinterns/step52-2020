package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;

class GetPeopleDataForPositiveUser implements Runnable {
  Credential credential;
  ArrayList<PotentialContact> contacts;

  public GetPeopleData(Credential credential, ArrayList<PotentialContact> contacts) {
    this.credential = credential;
    this.contacts = contacts;
  }

  public void run() {
    // Get contacts from people api
  }
}