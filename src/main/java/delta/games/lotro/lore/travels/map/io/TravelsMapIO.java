package delta.games.lotro.lore.travels.map.io;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.map.TravelsMap;
import delta.games.lotro.lore.travels.map.io.xml.TravelsMapXMLParser;

/**
 * IO methods for the travels map.
 * @author DAM
 */
public class TravelsMapIO
{
  /**
   * Load the travels map.
   * @return the travels map.
   */
  public static TravelsMap loadTravelsMap()
  {
    File from=getTravelsMapFile();
    TravelsMapXMLParser p=new TravelsMapXMLParser();
    return p.parseXML(from);
  }

  private static File getTravelsMapFile()
  {
    return LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_MAP);
  }
}
