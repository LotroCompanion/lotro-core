package delta.games.lotro.character.storage.statistics.reputation;

import delta.games.lotro.common.statistics.FactionStats;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Statistics for a single faction, from storage.
 * @author DAM
 */
public class StorageFactionStats extends FactionStats
{
  private int _itemsCount;

  /**
   * Constructor.
   * @param faction Managed faction.
   */
  public StorageFactionStats(Faction faction)
  {
    super(faction);
  }

  /**
   * Add a reputation amount for some items.
   * @param itemsCount Items count.
   * @param amountByItem Reputation amount by item.
   */
  public void addItems(int itemsCount, int amountByItem)
  {
    _points+=(amountByItem*itemsCount);
    _itemsCount+=itemsCount;
  }

  /**
   * Get the number of items used to build this reputation.
   * @return A count.
   */
  public int getItemsCount()
  {
    return _itemsCount;
  }
}
