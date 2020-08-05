package com.onlinecontacttracing.messaging;

/*
* System's opening message.
*/
public class EmailSubject implements HasEnglishTranslation, HasSpanishTranslation  {
 
  private String englishTranslation = "COVID-19 Potential Exposure Alert";
  private String spanishTranslation = "Alerta de Exposición Potencial de COVID-19";


  public static String getEnglishTranslation() {
    return this.englishTranslation;
  }

  public static String getSpanishTranslation() {
    return this.spanishTranslation;
  } 

  public static String getTranslation(String language) {
    if (language.equals("EN")) {
      return getEnglishTranslation();
    } else {
      return getSpanishTranslation();
    }
  }
  
}


