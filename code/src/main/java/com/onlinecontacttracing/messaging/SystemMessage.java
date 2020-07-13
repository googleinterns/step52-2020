package com.onlinecontacttracing.messaging;

public enum SystemMessage implements HasEnglishTranslation {
  VERSION_1("Hello, you are receiving this message because someone you know has tested positive for COVID-19. Please consider quarantining and self-isolating for 14 weeks in addition to getting tested.");

  private String englishTranslation;

  SystemMessage(String englishTranslation) {
    this.englishTranslation = englishTranslation;
  }

  public String getEnglishTranslation() {
    return englishTranslation;
  }

}