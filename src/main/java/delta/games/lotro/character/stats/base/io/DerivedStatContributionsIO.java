package delta.games.lotro.character.stats.base.io;

import java.io.File;

import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.io.xml.DerivedStatsContributionsXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * I/O methods for the derived stat contributions manager.
 * @author DAM
 */
public class DerivedStatContributionsIO
{
  private static File getFile()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    return cfg.getFile(DataFiles.STAT_CONTRIBS);
  }

  /**
   * Load the derived stat contributions manager.
   * @return the derived stat contributions manager.
   */
  public static DerivedStatsContributionsMgr load()
  {
    File toFile=getFile();
    DerivedStatsContributionsXMLParser parser=new DerivedStatsContributionsXMLParser();
    DerivedStatsContributionsMgr contribsManager=parser.parseXML(toFile);
    return contribsManager;
  }
}
