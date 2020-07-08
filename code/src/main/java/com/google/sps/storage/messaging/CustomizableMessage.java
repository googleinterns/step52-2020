package com.google.sps.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
* Save user's personal message
*/
@Entity
public class CustomizableMessage {

  @Id String userId;
  String message;

  // Objecify requires one constructor with no parameters
  private CustomizableMessage() {}

  public CustomizableMessage(String userId, String message) {
    this.userId = userId;
    this.message = message;
  }
  
}
