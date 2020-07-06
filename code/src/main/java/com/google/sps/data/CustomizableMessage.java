package com.google.sps.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class CustomizableMessage {

  @Id String userId;
  String message;

  private CustomizableMessage() {}

  public CustomizableMessage(String userId, String message) {
    this.userId = userId;
    this.message = message;
  }
  
}
