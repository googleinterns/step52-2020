package com.onlinecontacttracing.messaging;

import com.onlinecontacttracing.storage.PositiveUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
 
@RunWith(JUnit4.class)
public final class SystemMessageTest {
  SystemMessage systemMessage = SystemMessage.VERSION_1;
  
  @Test
  public void checkGetSystemMessageFromValidString() {
    assertEquals(systemMessage.getSystemMessageFromString("VERSION_1"), SystemMessage.VERSION_1);
  }

  @Test
  public void checkGetSystemMessageFromInvalidString() {
    assertEquals(systemMessage.getSystemMessageFromString("VERSION_11111"), SystemMessage.VERSION_1);
  }

  @Test
  public void checkGetEnglishSystemMessage() {
    assertEquals(systemMessage.getEnglishTranslation(), "Hello, you are receiving this message because someone you know has tested positive for COVID-19. Please consider quarantining and self-isolating for 14 weeks in addition to getting tested.");
  }

  @Test
  public void checkGetSpanishSystemMessage() {
    assertEquals(systemMessage.getSpanishTranslation(), "Hola, está recibiendo este mensaje porque alguien que conoce ha dado positivo por COVID-19. Considere poner en cuarentena y autoaislar durante 14 semanas además de hacerse la prueba.");
  }


}