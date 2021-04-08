package delta.games.lotro.character.storage.vaults;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.CountedItemInstance;

/**
 * Item instances container.
 * @author DAM
 */
public class ItemInstancesContainer
{
  private List<CountedItemInstance> _items;

  /**
   * Constructor.
   */
  public ItemInstancesContainer()
  {
    _items=new ArrayList<CountedItemInstance>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(CountedItemInstance item)
  {
    _items.add(item);
  }

  /**
   * Get all items, in insertion order.
   * @return A list of items.
   */
  public List<CountedItemInstance> getAllItemInstances()
  {
    List<CountedItemInstance> ret=new ArrayList<CountedItemInstance>(_items);
    return ret;
  }

  /**
   * Get the number of items in this container.
   * @return an items count.
   */
  public int getItemsCount()
  {
    return _items.size();
  }

  /**
   * Dump contents.
   * @param level Indentation level.
   */
  public void dump(int level)
  {
    List<CountedItemInstance> itemInstances=getAllItemInstances();
    for(CountedItemInstance itemInstance : itemInstances)
    {
      for(int i=0;i<level;i++) System.out.print('\t');
      System.out.println(itemInstance);
    }
  }
}
