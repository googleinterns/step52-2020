public enum SystemMessage implements HasEnglishTranslation {
  VERSION_1("Hello, you are receiving this message because someone you know has tested positive for COVID-19. 
             \n Please consider quarantining and self-isolating for 14 weeks in addition to getting tested. ")

  private String englishTranslation;

  public SystemMessage(String englishTranslation) {
    this.englishTranslation = englishTranslation;
  }

  public String getEnglishTranslation() {
    return englishTranslation;
  }

}