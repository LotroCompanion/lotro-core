package delta.games.lotro.character.storage.vaults;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Item instances container.
 * @author DAM
 */
public class ItemInstancesContainer
{
  private List<CountedItem<ItemInstance<? extends Item>>> _items;

  /**
   * Constructor.
   */
  public ItemInstancesContainer()
  {
    _items=new ArrayList<CountedItem<ItemInstance<? extends Item>>>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(CountedItem<ItemInstance<? extends Item>> item)
  {
    _items.add(item);
  }

  /**
   * Get all items, in insertion order.
   * @return A list of items.
   */
  public List<CountedItem<ItemInstance<? extends Item>>> getAllItemInstances()
  {
    List<CountedItem<ItemInstance<? extends Item>>> ret=new ArrayList<CountedItem<ItemInstance<? extends Item>>>(_items);
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
   * @param out Output stream.
   * @param level Indentation level.
   */
  public void dump(PrintStream out, int level)
  {
    List<CountedItem<ItemInstance<? extends Item>>> itemInstances=getAllItemInstances();
    for(CountedItem<ItemInstance<? extends Item>> itemInstance : itemInstances)
    {
      for(int i=0;i<level;i++) out.print('\t');
      out.println(itemInstance);
    }
  }
}
