package delta.games.lotro.lore.travels.map.io;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.map.TravelsMap;
import delta.games.lotro.lore.travels.map.io.xml.TravelsMapXMLWriter;

/**
 * Test XML I/O for the travels map.
 * @author DAM
 */
class TravelsMapIOTest
{
  /**
   * Test read/write the travels map.
   */
  @Test
  void testReadWrite()
  {
    TravelsMap map=TravelsMapIO.loadTravelsMap();
    assertNotNull(map);
    File file=LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_MAP);
    File newFile=new File(file.getParentFile(),file.getName()+".new.xml");
    boolean ok=TravelsMapXMLWriter.writeTravelsMapFile(newFile,map);
    assertTrue(ok);
  }
}
