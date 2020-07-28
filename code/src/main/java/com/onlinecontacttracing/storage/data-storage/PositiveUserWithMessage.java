package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.onlinecontacttracing.storage.CustomizableMessage;
import java.util.ArrayList;

@Entity
public class PositiveUserWithMessage {
  @Id private String userId;
  private ArrayList<String> listOfContacts = new ArrayList<String>();
  private CustomizableMessage customMessage;

    public PositiveUserWithMessage() {}

    public PositiveUserWithMessage (ArrayList<String> contacts, CustomizableMessage message) {
        listOfContacts = contacts;
        customMessage = message;
        userId = customMessage.getUserId();
    }
    
    public ArrayList<String> getContacts() {
        return listOfContacts;
    }

    public CustomizableMessage getMessage() {
        return customMessage;
    }
}
