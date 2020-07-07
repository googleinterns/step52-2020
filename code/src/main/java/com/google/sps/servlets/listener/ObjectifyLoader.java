package com.google.sps.storage;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyLoader implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.init();
    ObjectifyService.register(Business.class);
    ObjectifyService.register(BusinessNumber.class);
    ObjectifyService.register(CustomizableMessage.class);
    ObjectifyService.register(Location.class);
    ObjectifyService.register(NotificationBatch.class);
    ObjectifyService.register(PersonEmail.class);
    ObjectifyService.register(Place.class);
    ObjectifyService.register(PositiveUserContacts.class);
    ObjectifyService.register(PositiveUser.class);
    ObjectifyService.register(PositiveUserLocations.class);
    ObjectifyService.register(PositiveUserPlaces.class);
    ObjectifyService.register(PotentialContact.class);
  }
}
