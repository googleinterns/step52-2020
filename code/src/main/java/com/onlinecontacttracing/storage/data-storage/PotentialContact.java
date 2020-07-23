package com.onlinecontacttracing.storage;

/**
* This class stores the information needed to contact a person
*/
public class PotentialContact {

  private final String nameOfPerson;
  private final String emailOfPerson;

  public PotentialContact(String name, String email) {
    nameOfPerson = name;
    emailOfPerson = email;
  }

  public String getName() {
    return nameOfPerson;
  }

  public String getEmail() {
    return emailOfPerson;
  }

  @Override
  public String toString() {
    return nameOfPerson + " can be contacted at " + emailOfPerson;
  }
}