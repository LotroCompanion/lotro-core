package delta.games.lotro.character.stats.base.io;

import java.io.File;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.io.xml.DerivedStatsContributionsXMLParser;

/**
 * I/O methods for the derived stat contributions manager.
 * @author DAM
 */
public class DerivedStatContributionsIO
{
  private static File getFile()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File loreDir=cfg.getLoreDir();
    File charactersDir=new File(loreDir,"characters");
    File ret=new File(charactersDir,"statContribs.xml");
    return ret;
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
