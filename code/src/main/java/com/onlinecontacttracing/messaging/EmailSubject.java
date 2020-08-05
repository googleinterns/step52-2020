package com.onlinecontacttracing.messaging;

/*
* System's opening message.
*/
public class EmailSubject {
 
  private static final String ENGLISH_TRANSLATION = "COVID-19 Potential Exposure Alert";
  private static final String SPANISH_TRANSLATION = "Alerta de Exposición Potencial de COVID-19";


  public static String getEnglishTranslation() {
    return ENGLISH_TRANSLATION;
  }

  public static String getSpanishTranslation() {
    return SPANISH_TRANSLATION;
  } 

  public static String getTranslation(String language) {
    if (language.equals("EN")) {
      return getEnglishTranslation();
    } else {
      return getSpanishTranslation();
    }
  }
  
}


