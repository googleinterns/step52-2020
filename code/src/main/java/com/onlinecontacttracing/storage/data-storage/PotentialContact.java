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

  /*
  *  I added equals and hashCode so that we can use a hashSet when adding PotentialContacts
  */
  @Override
  public boolean equals(Object o) {
    if (o == this) { 
        return true; 
    } 

    if (!(o instanceof PotentialContact)) { 
        return false; 
    } 
        
    // typecast o to Complex so that we can compare data members  
    PotentialContact otherContact = (PotentialContact) o; 
        
    // Compare the data members and return accordingly  
    return emailOfPerson.equals(otherContact.emailOfPerson);
  } 

  @Override
  public int hashCode() { 
    return emailOfPerson.hashCode(); 
  } 

  @Override
  public String toString() {
    return nameOfPerson + " can be contacted at " + emailOfPerson;
  }
}