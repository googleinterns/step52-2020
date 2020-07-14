package com.onlinecontacttracing.messaging;

public enum SystemMessage implements HasEnglishTranslation, HasSpanishTranslation  {
  VERSION_1("Hello, you are receiving this message because someone you know has tested positive for COVID-19. Please consider quarantining and self-isolating for 14 weeks in addition to getting tested.",
            "Hola, está recibiendo este mensaje porque alguien que conoce ha dado positivo por COVID-19. Considere poner en cuarentena y autoaislar durante 14 semanas además de hacerse la prueba.");

  private String englishTranslation;
  private String spanishTranslation;

  SystemMessage(String englishTranslation, String spanishTranslation) {
    this.englishTranslation = englishTranslation;
    this.spanishTranslation = spanishTranslation;
  }

  public String getEnglishTranslation() {
    return englishTranslation;
  }

  public String getSpanishTranslation() {
    return spanishTranslation;
  }

}