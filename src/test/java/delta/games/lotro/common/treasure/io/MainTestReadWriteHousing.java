package delta.games.lotro.common.treasure.io;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.housing.HousingManager;
import delta.games.lotro.lore.housing.io.xml.HousingXMLParser;
import delta.games.lotro.lore.housing.io.xml.HousingXMLWriter;

/**
 * Test to check read/write of housing data.
 * @author DAM
 */
public class MainTestReadWriteHousing
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File in=LotroCoreConfig.getInstance().getFile(DataFiles.HOUSING);
    HousingManager mgr=new HousingXMLParser().parseHousingFile(in);
    File to=new File(in.getParentFile(),"newHousing.xml");
    new HousingXMLWriter().writeHousingData(to,mgr);
  }
}
