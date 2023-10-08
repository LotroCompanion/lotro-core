package delta.games.lotro.lore.worldEvents;

import java.io.File;
import java.util.List;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventsXMLWriter;
import junit.framework.TestCase;

/**
 * Test XML I/O for world events. 
 * @author DAM
 */
public class WorldEventsXmlIOTest extends TestCase
{
  /**
   * Test world events loading: loads world vents and write them to a file.
   * Compare the produced file with the initial one.
   */
  public void testWorldEventsLoading()
  {
    List<WorldEvent> worldEvents=WorldEventsManager.getInstance().getAll();
    WorldEventsXMLWriter writer=new WorldEventsXMLWriter();
    File to=new File("worldEvents.xml");
    writer.write(to,worldEvents,EncodingNames.UTF_8);
    // Compare file worldEvents.xml with the one loaded by the manager
  }
}
