package com.google.sps.storage;

/**
* This class stores the information needed to contact a person
*/
public class PotentialContact {

  private String nameOfPerson;
  private String email;

  public PotentialContact(String name, String email) {
    nameOfPerson = name;
    this.email = email;
  }

  public String getName() {
    return nameOfPerson;
  }

  public String getEmail() {
    return email;
  }

}