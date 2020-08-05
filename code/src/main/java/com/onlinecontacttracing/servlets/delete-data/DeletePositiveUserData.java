package com.onlinecontacttracing.servlets;

import com.onlinecontacttracing.storage.PositiveUserContacts;
import com.onlinecontacttracing.storage.PositiveUserPlaces;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class DeletePositiveUserData {

  public static void execute(String userId) {
    ofy().delete().type(PositiveUserContacts.class).id(userId).now();
    ofy().delete().type(PositiveUserPlaces.class).id(userId).now();
  }
}