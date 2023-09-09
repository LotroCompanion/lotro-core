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
   */
  public ItemScalingEntry()
  {
    // Nothing!
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
   * Set the character level.
   * @param level Level to set.
   */
  public void setLevel(int level)
  {
    _level=level;
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
   * Set the item level.
   * @param itemLevel Item level to set.
   */
  public void setItemLevel(int itemLevel)
  {
    _itemLevel=itemLevel;
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
   * Set the stats.
   * @param stats Stats to set.
   */
  public void setStats(BasicStatsSet stats)
  {
    _stats=stats;
  }

  /**
   * Get the item value for the item level.
   * @return a value.
   */
  public Money getMoney()
  {
    return _money;
  }

  /**
   *  Set the value.
   * @param money Value to set.
   */
  public void setMoney(Money money)
  {
    _money=money;
  }
}
