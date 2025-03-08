package delta.games.lotro.common.statistics.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Items statistics.
 * @author DAM
 */
public class ItemsStats
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ItemsStats.class);

  private Map<Integer,CountedItem<Item>> _items;
  private List<CountedItem<Item>> _list;

  /**
   * Constructor.
   */
  public ItemsStats()
  {
    _items=new HashMap<Integer,CountedItem<Item>>();
    _list=new ArrayList<CountedItem<Item>>();
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
      LOGGER.warn("Item not found: {}",Integer.valueOf(itemId));
      return false;
    }
    Integer itemIdInteger=Integer.valueOf(itemId);
    CountedItem<Item> count=_items.get(itemIdInteger);
    if (count==null)
    {
      count=new CountedItem<Item>(item,0);
      _items.put(itemIdInteger,count);
      _list.add(count);
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
   * Get the total number of items.
   * @return A count.
   */
  public int getItemsCount()
  {
    int total=0;
    for(CountedItem<Item> itemCount : _items.values())
    {
      total+=itemCount.getQuantity();
    }
    return total;
  }

  /**
   * Get the acquired items.
   * @return A list of counted items.
   */
  public List<CountedItem<Item>> getItems()
  {
    return _list;
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    _items.clear();
    _list.clear();
  }
}
