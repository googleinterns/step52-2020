package com.onlinecontacttracing.messaging;

/**
* Guarantees a message has an English translation
*/
public interface HasEnglishTranslation {
  /**
  * Returns a message's English translation
  */
  public String getEnglishTranslation();
}
