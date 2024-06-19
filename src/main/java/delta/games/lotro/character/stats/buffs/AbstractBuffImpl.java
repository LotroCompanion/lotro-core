package delta.games.lotro.character.stats.buffs;

import java.util.Collections;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Base class for buff implementations.
 * @author DAM
 */
public abstract class AbstractBuffImpl
{
  /**
   * Compute the stats contribution of a buff for a character level.
   * @param level Targeted character level.
   * @param buff Buff to use.
   * @return A stats set or <code>null</code> if not supported.
   */
  public BasicStatsSet getStats(int level, BuffInstance buff)
  {
    return null;
  }

  /**
   * Get all managed tiers.
   * @return a sorted list of all managed tiers or <code>null</code> if tiers are not supported.
   */
  public List<Integer> getTiers()
  {
    return Collections.emptyList();
  }
}
