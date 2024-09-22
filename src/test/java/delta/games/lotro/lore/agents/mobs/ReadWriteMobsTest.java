package delta.games.lotro.lore.agents.mobs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.misc.CRC;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.agents.mobs.io.xml.MobsXMLWriter;

/**
 * Test to read/write mob data.
 * @author DAM
 */
class ReadWriteMobsTest
{
  /**
   * Read the mobs file, then write it.
   * Check it does not change.
   */
  @Test
  void testReadWrite()
  {
    List<MobDescription> mobs=MobsManager.getInstance().getMobs();
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.MOBS);
    long initialCRC=CRC.computeCRC(inputFile);
    long initialSize=inputFile.length();
    File newFile=new File(inputFile.getParentFile(),"new-"+inputFile.getName());
    newFile.deleteOnExit();
    new MobsXMLWriter().writeMobs(newFile,mobs,EncodingNames.UTF_8);
    long newCRC=CRC.computeCRC(newFile);
    long newSize=newFile.length();
    assertEquals(initialCRC,newCRC);
    assertEquals(initialSize,newSize);
  }
}
