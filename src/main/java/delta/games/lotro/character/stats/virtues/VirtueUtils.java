package delta.games.lotro.character.stats.virtues;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Utility methods related to virtues.
 * @author DAM
 */
public class VirtueUtils
{
  /**
   * Get the bonus rank for a virtue.
   * @param buffs Buffs to get virtue rank stats.
   * @param virtue Targeted virtue.
   * @return A bonus rank or 0 if none.
   */
  public static int getVirtueRankBonus(BasicStatsSet buffs, VirtueDescription virtue)
  {
    int bonus=0;
    if ((buffs!=null) && (virtue!=null))
    {
      String statKey=virtue.getRankStatKey();
      if ((statKey!=null) && (statKey.length()>0))
      {
        StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
        if (stat!=null)
        {
          FixedDecimalsInteger value=buffs.getStat(stat);
          if (value!=null)
          {
            bonus=value.intValue();
          }
        }
      }
    }
    return bonus;
  }
}
