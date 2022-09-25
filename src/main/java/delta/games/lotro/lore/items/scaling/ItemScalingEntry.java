package delta.games.lotro.lore.items.scaling;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.money.Money;

/**
 * Entry of an item scaling table.
 * @author DAM
 */
public class ItemScalingEntry
{
  private int _level;
  private int _itemLevel;
  private BasicStatsSet _stats;
  private Money _money;

  /**
   * Constructor.
   * @param level Character/instance lLevel.
   * @param itemLevel Item level.
   * @param stats Stats.
   * @param money Value.
   */
  public ItemScalingEntry(int level, int itemLevel, BasicStatsSet stats, Money money)
  {
    _level=level;
    _itemLevel=itemLevel;
    _stats=stats;
    _money=money;
  }

  /**
   * Get the character/instance level.
   * @return a level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Get the item level used for scaling.
   * @return an item level.
   */
  public int getItemLevel()
  {
    return _itemLevel;
  }

  /**
   * Get the item stats for the item level.
   * @return the stats Stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  /**
   * Get the item value for the item level.
   * @return a value.
   */
  public Money getMoney()
  {
    return _money;
  }
}
