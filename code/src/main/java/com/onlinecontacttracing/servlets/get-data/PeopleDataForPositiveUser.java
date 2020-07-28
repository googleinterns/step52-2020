package com.onlinecontacttracing.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.onlinecontacttracing.storage.PotentialContact;
import java.util.ArrayList;

class PeopleDataForPositiveUser implements Runnable {
  Credential credential;
  ArrayList<PotentialContact> contacts;

  public PeopleDataForPositiveUser(Credential credential, ArrayList<PotentialContact> contacts) {
    this.credential = credential;
    this.contacts = contacts;
  }

  public void run() {
    // TODO Get contacts from people api
  }
}