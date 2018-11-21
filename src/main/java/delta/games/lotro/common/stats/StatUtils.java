package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.STAT;

/**
 * Utility methods for stats.
 * @author DAM
 */
public class StatUtils
{
  /**
   * Fix the value of a stat:
   * <ul>
   * <li>percentage stats are given in % values (i.e 1 becomes 100%).
   * <li>regen ?C?R stats are given in units/minutes (i.e x60).
   * </ul>
   * @param stat Targeted stat.
   * @param statValue Raw value.
   * @return the fixed value.
   */
  public static float fixStatValue(STAT stat, float statValue)
  {
    if (stat.isPercentage())
    {
      statValue=statValue*100;
    }
    if ((stat==STAT.ICMR) || (stat==STAT.ICPR) || (stat==STAT.OCMR) || (stat==STAT.OCPR))
    {
      statValue=statValue*60;
    }
    return statValue;
  }
}
