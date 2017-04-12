package delta.games.lotro.lore.items.stats;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Provides item stats.
 * @author DAM
 */
public interface ItemStatsProvider
{
  /**
   * Get stats.
   * @param itemLevel Level of the item.
   * @return A set of stats.
   */
  BasicStatsSet getStats(int itemLevel);

  /**
   * Get a string that can persist this provider.
   * @return a string.
   */
  String toPersistableString();
}
