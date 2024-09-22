package delta.games.lotro.common.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.misc.CRC;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.action.io.xml.ActionTablesXMLWriter;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Test to read/write action tables.
 * @author DAM
 */
class ReadWriteActionTablesTest
{
  /**
   * Read the mobs file, then write it.
   * Check it does not change.
   */
  @Test
  void testReadWrite()
  {
    List<ActionTable> actionTables=ActionTablesManager.getInstance().getAll();
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.ACTION_TABLES);
    long initialCRC=CRC.computeCRC(inputFile);
    long initialSize=inputFile.length();
    File newFile=new File(inputFile.getParentFile(),"new-"+inputFile.getName());
    newFile.deleteOnExit();
    new ActionTablesXMLWriter().writeActionTables(newFile,actionTables,EncodingNames.UTF_8);
    long newCRC=CRC.computeCRC(newFile);
    long newSize=newFile.length();
    assertEquals(initialCRC,newCRC);
    assertEquals(initialSize,newSize);
  }
}
