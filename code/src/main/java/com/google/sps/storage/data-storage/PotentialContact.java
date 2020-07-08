package com.google.sps.storage;

/**
* Store a contacts's name and email
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
