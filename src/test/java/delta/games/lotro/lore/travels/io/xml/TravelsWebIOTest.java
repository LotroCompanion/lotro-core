package delta.games.lotro.lore.travels.io.xml;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.TravelsManager;

/**
 * Test XML I/O for the travels web.
 * @author DAM
 */
class TravelsWebIOTest
{
  /**
   * Test read/write the travels web.
   */
  @Test
  void testReadWrite()
  {
    TravelsWebXMLParser p=new TravelsWebXMLParser();
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_WEB);
    TravelsManager mgr=p.parseXML(from);
    assertNotNull(mgr);
    File newFile=new File(from.getParentFile(),from.getName()+".new.xml");
    boolean ok=TravelsWebXMLWriter.writeTravelsWebFile(newFile,mgr);
    assertTrue(ok);
  }
}
