package delta.games.lotro.character.stats;

import java.util.HashMap;

import delta.games.lotro.character.CharacterStat;
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
