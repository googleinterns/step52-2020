package com.onlinecontacttracing.storage;

import java.time.Instant;
import java.util.Optional;

/**
* This class keeps track of information needed to contact people and businesses.
*/
public class PersonEmail implements ContactStatus{

  private String email;
  private String systemMessageLanguage;
  private String systemMessageVersion;
  private String localityResourceLanguage;
  private String localityResourceVersion;
  private String emailSubjectLanguage;
  private String emailSubjectVersion;
  private Optional<Long> getTimeWhenEmailedSeconds;
  private boolean personHasBeenEmailed;

  // Empty constructor for Objectify
  private PersonEmail() {}

  public PersonEmail(String email, String systemMessageLanguage, String systemMessageVersion, 
      String localityResourceLanguage, String localityResourceVersion, String emailSubjectLanguage, String emailSubjectVersion) {
    this.email = email;
    this.systemMessageLanguage = systemMessageLanguage;
    this.systemMessageVersion = systemMessageVersion;
    this.localityResourceLanguage = localityResourceLanguage;
    this.localityResourceVersion = localityResourceVersion;
    this.emailSubjectLanguage = emailSubjectLanguage;
    this.emailSubjectVersion = emailSubjectVersion;
    getTimeWhenEmailedSeconds = Optional.empty();
    personHasBeenEmailed = false;
  }
  
  @Override
  public void markContactedSuccessfully() {
    getTimeWhenEmailedSeconds = Optional.of(Instant.now().getEpochSecond());
    personHasBeenEmailed = true;
  }

  @Override
  public boolean hasBeenContactedSuccessfully() {
    return personHasBeenEmailed;
  }

  @Override
  public Optional<Long> getTimeWhenContactedSeconds() {
    return getTimeWhenEmailedSeconds;
  }

  public String getEmail() {
    return email;
  }

  public String getSystemMessageLanguage() {
    return systemMessageLanguage;
  }

  public String getSystemMessageVersion() {
    return systemMessageVersion;
  }

  public String getLocalityResourceLanguage() {
    return localityResourceLanguage;
  }

  public String getLocalityResourceVersion() {
    return localityResourceVersion;
  }

  public String getEmailSubjectLanguage() {
    return emailSubjectLanguage;
  }

  public String getEmailSubjectVersion() {
    return emailSubjectVersion;
  }
}