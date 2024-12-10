package delta.games.lotro.common.global;

import java.io.File;

import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.global.io.xml.CombatDataXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.PerfUtils;

/**
 * Provides facilities related to the combat system.
 * @author DAM
 */
public class CombatSystem
{
  private static CombatSystem _instance=null;

  private CombatData _data;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CombatSystem getInstance()
  {
    if (_instance==null)
    {
      _instance=new CombatSystem();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private CombatSystem()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.COMBAT_DATA);
    long now=System.currentTimeMillis();
    _data=CombatDataXMLParser.parseCombatDataFile(inputFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog("combat system data",duration);
  }

  /**
   * Get data for the combat system.
   * @return some data.
   */
  public CombatData getData()
  {
    return _data;
  }

  /**
   * Get the ratings manager.
   * @return the ratings manager.
   */
  public RatingsMgr getRatingsMgr()
  {
    return _data.getRatingsMgr();
  }
}
