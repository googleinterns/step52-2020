package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.time.Instant;
import java.util.ArrayList;
import com.google.maps.errors.ApiException;
import com.google.api.services.calendar.model.Event;
import com.google.maps.GeoApiContext;
import java.io.IOException;
import java.util.Optional;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.PlacesApi;

/**
* This class keeps track of a positive user's list of contacts from People and Calendar API
*/
@Entity
public class PositiveUserPlaces {

  @Id private String userId;
  @Index private long timeCreatedSeconds;
  private ArrayList<Place> listOfPlaces;

  // Objecify requires one constructor with no parameters
  private PositiveUserPlaces() {}

  public PositiveUserPlaces(String id) {
    userId = id;
    timeCreatedSeconds = Instant.now().getEpochSecond();
    listOfPlaces = new ArrayList<Place>();
  }

  public String getUserId() {
    return userId;
  }

  public ArrayList<Place> getListOfPlaces() {
    return listOfPlaces;
  }

  public long getTimeCreatedSeconds() {
    return timeCreatedSeconds;
  }

  public void add(String placeId, String name, long intervalStart, long intervalEnd) {
    listOfPlaces.add(new Place(placeId, name, intervalStart, intervalEnd));
  }

  @Override
  public String toString() {
    return String.format("The user (%s) has been to %s", userId, listOfPlaces.toString());
  }

  public void addPlaceFromEvent(Event event, GeoApiContext context) throws ApiException, InterruptedException, IOException {
    Optional<String> addressOptional = Optional.ofNullable(event.getLocation());
    
    if (addressOptional.isPresent()) {
      String address = addressOptional.get();
      PlacesSearchResult[] results = PlacesApi.textSearchQuery(context, address).await().results;
      if (results.length != 0) {
        String placeId = results[0].placeId;
        long startTimeSeconds = event.getStart().getDateTime().getValue() / 1000;
        long endTimeSeconds = event.getEnd().getDateTime().getValue() / 1000;

        add(placeId, address, startTimeSeconds, endTimeSeconds);
      }
    }
  }
}