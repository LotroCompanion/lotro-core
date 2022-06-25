package delta.games.lotro.character.stats.virtues;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;

/**
 * Utility methods related to virtues.
 * @author DAM
 */
public class VirtueUtils
{
  /**
   * Get the bonus rank for a virtue.
   * @param stats Buffs to get virtue rank stats.
   * @param virtue Targeted virtue.
   * @return A bonus rank or 0 if none.
   */
  public static int getVirtueRankBonus(BasicStatsSet stats, VirtueDescription virtue)
  {
    int bonus=0;
    if ((stats!=null) && (virtue!=null))
    {
      String statKey=virtue.getRankStatKey();
      if ((statKey!=null) && (statKey.length()>0))
      {
        StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
        if (stat!=null)
        {
          Number value=stats.getStat(stat);
          if (value!=null)
          {
            bonus=value.intValue();
          }
        }
      }
    }
    return bonus;
  }

  /**
   * Extract the virtue rank stats from a set of stats.
   * @param stats Input stats.
   * @return the filtered stats.
   */
  public static BasicStatsSet extractVirtueRankStats(BasicStatsSet stats)
  {
    BasicStatsSet ret=new BasicStatsSet();
    VirtuesManager virtuesManager=VirtuesManager.getInstance();
    for(VirtueDescription virtue : virtuesManager.getAll())
    {
      String statKey=virtue.getRankStatKey();
      StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
      if (stat!=null)
      {
        Number value=stats.getStat(stat);
        if (value!=null)
        {
          ret.setStat(stat,value);
        }
      }
    }
    return ret;
  }
}
