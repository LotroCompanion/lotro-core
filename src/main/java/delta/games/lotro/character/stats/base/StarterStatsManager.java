package delta.games.lotro.character.stats.base;

import java.net.URL;
import java.util.HashMap;

import delta.common.utils.url.URLTools;
import delta.games.lotro.character.CharacterStat;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.StarterStatsParser;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Manager for starter stats.
 * @author DAM
 */
public class StarterStatsManager
{
  private HashMap<String,BasicStatsSet> _statsMap;

  /**
   * Constructor.
   */
  public StarterStatsManager()
  {
    _statsMap=new HashMap<String,BasicStatsSet>();
  }

  /**
   * Build and initialize the starter stats manager from bundled data.
   * @return a new starter stats manager.
   */
  public static StarterStatsManager build()
  {
    StarterStatsParser parser=new StarterStatsParser();
    URL url=URLTools.getFromClassPath("starter.xml",StarterStatsManager.class.getPackage());
    StarterStatsManager mgr=parser.parseXML(url);
    return mgr;
  }

  private String buildKey(Race race, CharacterClass characterClass)
  {
    String key=race.getKey()+"#"+characterClass.getKey();
    return key;
  }

  /**
   * Set starter stat for a race/class/stat.
   * @param race Targeted race.
   * @param characterClass Targeted character class.
   * @param stat Targeted stat and value to set.
   */
  public void setStat(Race race, CharacterClass characterClass, CharacterStat stat)
  {
    String key=buildKey(race,characterClass);
    BasicStatsSet set=_statsMap.get(key);
    if (set==null)
    {
      set=new BasicStatsSet();
      _statsMap.put(key,set);
    }
    set.setStat(stat.getStat(),stat.getValue());
  }

  /**
   * Get starter stats for a race/class.
   * @param race Targeted race.
   * @param characterClass Targeted character class.
   * @return A set of stats.
   */
  public BasicStatsSet getStartingStats(Race race, CharacterClass characterClass)
  {
    return _statsMap.get(buildKey(race,characterClass));
  }
}
