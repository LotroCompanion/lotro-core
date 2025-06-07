package delta.games.lotro.lore.travels.map.io;

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
    File file=LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_MAP);
    File newFile=new File(file.getParentFile(),file.getName()+".new.xml");
    TravelsMapXMLWriter.writeTravelsMapFile(newFile,map);
  }
}
