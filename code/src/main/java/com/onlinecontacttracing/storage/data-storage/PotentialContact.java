package com.onlinecontacttracing.storage;

/**
* This class stores the information needed to contact a person
*/
public class PotentialContact implements Comparable<PotentialContact> {

  private String nameOfPerson;
  private String emailOfPerson;

  // Objecify requires one constructor with no parameters
  private PotentialContact() {}

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
   *  Potential contacts are considered the same if they have the same email.
   *  Override equals and hashCode to reflect this principal.
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

  /*
   * Make class sortable
   */
  @Override
  public int compareTo(PotentialContact other) {
    if (nameOfPerson == null && other.nameOfPerson == null) {
      return emailOfPerson.compareTo(other.emailOfPerson);
    } else if (nameOfPerson == null) {
      return 1;
    } else if (other.nameOfPerson == null) {
      return -1;
    } else {
	  return nameOfPerson.compareTo(other.nameOfPerson);
    }
  }

  @Override
  public String toString() {
    return nameOfPerson + " can be contacted at " + emailOfPerson;
  }
}