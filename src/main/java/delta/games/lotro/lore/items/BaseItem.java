package delta.games.lotro.lore.items;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.money.Money;

/**
 * Base/common contract between items and item instances.
 * @author DAM
 */
public interface BaseItem extends ItemProvider
{
  /**
   * Get the effective item level.
   * @return an item level or <code>null</code>.
   */
  Integer getEffectiveItemLevel();

  /**
   * Get the effective value.
   * @return a value or <code>null</code>.
   */
  Money getEffectiveValue();

  /**
   * Get the item stats.
   * @return a set of stats.
   */
  BasicStatsSet getStats();
}
