package com.onlinecontacttracing.messaging;

<<<<<<< HEAD
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

=======
import com.onlinecontacttracing.storage.PositiveUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
>>>>>>> master
 
@RunWith(JUnit4.class)
public final class LocalityResourceTest {
  LocalityResource localityResource = LocalityResource.NEW_YORK;
  
  @Test
  public void checkGetLocalityResourceFromValidString() {
    assertEquals(LocalityResource.getLocalityResourceFromString("NEW_YORK"), LocalityResource.NEW_YORK);
  }

  @Test
  public void checkGetLocalityResourceFromInvalidString() {
    assertEquals(LocalityResource.getLocalityResourceFromString("NEW_YORKKKKK"), LocalityResource.US);
  }

  @Test
  public void checkGetEnglishLocalityResource() {
    assertEquals(localityResource.getEnglishTranslation(), "Here's your state's help link: https://www1.nyc.gov/site/coronavirus/index.page");
  }

  @Test
  public void checkGetSpanishLocalityResource() {
    assertEquals(localityResource.getSpanishTranslation(), "Aquí está el enlace de ayuda de su estado: https://www1.nyc.gov/site/coronavirus/index.page");
  }


}