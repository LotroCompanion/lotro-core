package delta.games.lotro.character.stats;

import java.util.Comparator;

import delta.games.lotro.common.stats.StatDescription;

/**
 * Stats comparator.
 * @author DAM
 */
public class StatNameComparator implements Comparator<StatDescription>
{
  /**
   * Compare 2 stats using their label.
   * @param stat1 First stat.
   * @param stat2 Second stat.
   * @return Result of the comparison of the names of the given stats.
   */
  public int compare(StatDescription stat1, StatDescription stat2)
  {
    return stat1.getName().compareTo(stat2.getName());
  }
}
