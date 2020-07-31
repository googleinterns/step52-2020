package com.onlinecontacttracing.storage;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyLoader implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.init();
    ObjectifyService.register(Business.class);
    ObjectifyService.register(CustomizableMessage.class);
    ObjectifyService.register(NotificationBatch.class);
    ObjectifyService.register(PositiveUserContacts.class);
    ObjectifyService.register(PositiveUser.class);
    ObjectifyService.register(PositiveUserLocations.class);
    ObjectifyService.register(PositiveUserPlaces.class);
    ObjectifyService.register(PositiveUserWithMessage.class);
    ObjectifyService.register(NegativeUserLocation.class);
    ObjectifyService.register(NegativeUserPlace.class);
    ObjectifyService.register(NegativeUser.class);
  }
}
