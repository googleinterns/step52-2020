package com.onlinecontacttracing.messaging;

/*
 * Email opening message in either spanish or english
 */
public enum EmailSubject implements HasSpanishTranslation, HasEnglishTranslation{
  VERSION_1("COVID-19 Potential Exposure Alert", "Alerta de COVID-19: Contacto Potencial");
  private String englishTranslation;
  private String spanishTranslation;

  EmailSubject(String spanishTranslation, String englishTranslation) {
    this.englishTranslation = englishTranslation;
    this.spanishTranslation = spanishTranslation;
  }

  @Override
  public String getEnglishTranslation() {
    return this.englishTranslation;
  }

  @Override
  public String getSpanishTranslation() {
    return this.spanishTranslation;
  } 

  public String getTranslation(String language) {
    if (language.equals("EN")) {
      return this.getEnglishTranslation();
    } else {
      return this.getSpanishTranslation();
    }
  }

  public static EmailSubject getEmailSubjectFromString (String emailSubjectName) {
    for (EmailSubject emailSubject : EmailSubject.values()) { 
      if (emailSubject.name().equals(emailSubjectName)) {
        return emailSubject;
      }
    }
    return EmailSubject.VERSION_1;
  }

}