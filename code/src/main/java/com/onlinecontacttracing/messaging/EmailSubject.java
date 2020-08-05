package com.onlinecontacttracing.messaging;

/*
 * Email opening message in either spanish or english
 */
public class EmailSubject implements HasSpanishTranslation, HasEnglishTranslation{

  private static final String ENGLISH_TRANSLATION = "COVID-19 Potential Exposure Alert";
  private static final String SPANISH_TRANSLATION = "Alerta de COVID-19: Contacto Potencial";

  @Override
  public String getEnglishTranslation() {
    return ENGLISH_TRANSLATION;
  }

  @Override
  public String getSpanishTranslation() {
    return SPANISH_TRANSLATION;
  } 

  public static String getTranslation(String language) {
    if (language.equals("EN")) {
      return ENGLISH_TRANSLATION;
    } else {
      return SPANISH_TRANSLATION;
    }
  }
}