package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import com.onlinecontacttracing.storage.CustomizableMessage;
import com.onlinecontacttracing.messsaging.GeneratedUserId;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Entity
public class PositiveUserWithMessage {
  @Id private String userId;
  private ArrayList<String> listOfContacts = new ArrayList<String>();
  private CustomizableMessage customMessage;

    public PositiveUserWithMessage() {}

    public PositiveUserWithMessage (Map<String, String[]> params, int numEmails, String id) {
        userId = id;
        customMessage = new CustomizableMessage(userId, params.get("custom-message-box")[0]);
        for(Map.Entry<String, String[]> entry : params.entrySet()) {
            if(entry.getKey().contains("email-box-")) {
                listOfContacts.add(entry.getValue()[0]);
            }
        }
    }
    
    public ArrayList<String> getContacts() {
        return listOfContacts;
    }

    public CustomizableMessage getMessage() {
        return customMessage;
    }
}
