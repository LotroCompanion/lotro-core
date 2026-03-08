package delta.games.lotro.lore.worldEvents;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventsXMLWriter;

/**
 * Test XML I/O for world events. 
 * @author DAM
 */
class WorldEventsXmlIOTest
{
  /**
   * Test world events loading: loads world vents and write them to a file.
   * Compare the produced file with the initial one.
   */
  @Test
  void testWorldEventsLoading()
  {
    List<WorldEvent> worldEvents=WorldEventsManager.getInstance().getAll();
    assertNotNull(worldEvents);
    WorldEventsXMLWriter writer=new WorldEventsXMLWriter();
    File to=new File("worldEvents.xml");
    boolean ok=writer.write(to,worldEvents,EncodingNames.UTF_8);
    assertTrue(ok);
    // Compare file worldEvents.xml with the one loaded by the manager
  }
}
