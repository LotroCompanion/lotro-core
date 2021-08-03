package delta.games.lotro.character.status.statistics.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.comparators.CountedItemNameComparator;

/**
 * Items statistics.
 * @author DAM
 */
public class ItemsStats
{
  private static final Logger LOGGER=Logger.getLogger(ItemsStats.class);

  private Map<Integer,CountedItem<Item>> _items;

  /**
   * Constructor.
   */
  public ItemsStats()
  {
    _items=new HashMap<Integer,CountedItem<Item>>();
  }

  /**
   * Add some items.
   * @param itemId Item identifier.
   * @param itemsCount Count to add.
   * @return <code>true</code> if it was done, <code>false</code> otherwise.
   */
  public boolean add(int itemId, int itemsCount)
  {
    Item item=ItemsManager.getInstance().getItem(itemId);
    if (item==null)
    {
      LOGGER.warn("Item not found: "+itemId);
      return false;
    }
    Integer itemIdInteger=Integer.valueOf(itemId);
    CountedItem<Item> count=_items.get(itemIdInteger);
    if (count==null)
    {
      count=new CountedItem<Item>(item,0);
      _items.put(itemIdInteger,count);
    }
    count.add(itemsCount);
    return true;
  }

  /**
   * Get the number of different items.
   * @return A count.
   */
  public int getDistinctItemsCount()
  {
    return _items.size();
  }

  /**
   * Get the acquired items.
   * @return A list of counted items, sorted by name.
   */
  public List<CountedItem<Item>> getItems()
  {
    List<CountedItem<Item>> items=new ArrayList<CountedItem<Item>>(_items.values());
    Collections.sort(items,new CountedItemNameComparator<Item>());
    return items;
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    _items.clear();
  }
}
