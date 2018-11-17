package delta.games.lotro.character.stats.base.io;

import java.io.File;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.character.stats.base.StartStatsManager;
import delta.games.lotro.character.stats.base.io.xml.StartStatsXMLParser;
import delta.games.lotro.character.stats.base.io.xml.StartStatsXMLWriter;

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

  /**
   * Save the start stats manager.
   * @param statsManager Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(final StartStatsManager statsManager)
  {
    File toFile=getFile();
    return StartStatsXMLWriter.write(toFile,statsManager);
  }
}
