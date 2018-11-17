package delta.games.lotro.character.stats.base.io;

import java.io.File;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.character.stats.base.StartStatsManager;
import delta.games.lotro.character.stats.base.io.xml.StartStatsXMLParser;

/**
 * I/O methods for the start stats manager.
 * @author DAM
 */
public class StartStatsManagerIO
{
  private static File getFile()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File loreDir=cfg.getLoreDir();
    File charactersDir=new File(loreDir,"characters");
    File ret=new File(charactersDir,"startStats.xml");
    return ret;
  }

  /**
   * Load the start stats manager.
   * @return the start stats manager.
   */
  public static StartStatsManager load()
  {
    File toFile=getFile();
    StartStatsXMLParser parser=new StartStatsXMLParser();
    StartStatsManager startStatsManager=parser.parseXML(toFile);
    return startStatsManager;
  }
}
