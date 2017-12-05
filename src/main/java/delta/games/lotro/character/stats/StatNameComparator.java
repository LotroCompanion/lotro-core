package delta.games.lotro.character.stats;

import java.util.Comparator;

/**
 * Stats comparator.
 * @author DAM
 */
public class StatNameComparator implements Comparator<STAT>
{
  /**
   * Compare 2 stats using their label.
   * @param stat1 First stat.
   * @param stat2 Second stat.
   * @return Result of the comparison of the names of the given stats.
   */
  public int compare(STAT stat1, STAT stat2)
  {
    return stat1.getName().compareTo(stat2.getName());
  }
}
