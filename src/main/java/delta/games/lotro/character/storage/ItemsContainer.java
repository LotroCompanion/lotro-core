package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.items.CountedItem;

/**
 * Items container.
 * @author DAM
 */
public class ItemsContainer
{
  private HashMap<String,CountedItem> _items;

  /**
   * Constructor.
   */
  public ItemsContainer()
  {
    _items=new HashMap<String,CountedItem>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(CountedItem item)
  {
    String name=item.getName();
    CountedItem old=_items.put(name,item);
    if (old!=null)
    {
      // TODO Warn for item removal
    }
  }

  /**
   * Get an item by name.
   * @param name Name of the item to get.
   * @return An item or <code>null</code> if not found.
   */
  public CountedItem getByName(String name)
  {
    return _items.get(name);
  }

  /**
   * Get an item by identifier.
   * @param itemId Identifier of the item to get.
   * @return An item or <code>null</code> if not found.
   */
  public CountedItem getById(int itemId)
  {
    for(CountedItem item : _items.values())
    {
      int id=item.getId();
      if (id==itemId)
      {
        return item;
      }
    }
    return null;
  }

  /**
   * Get all items in this wallet, sorted by name.
   * @return A list of items.
   */
  public List<CountedItem> getAllItemsByName()
  {
    List<String> names=new ArrayList<String>(_items.keySet());
    Collections.sort(names);
    List<CountedItem> ret=new ArrayList<CountedItem>();
    for(String name : names)
    {
      ret.add(_items.get(name));
    }
    return ret;
  }

  /**
   * Get the number of items in this wallet.
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
    List<CountedItem> items=getAllItemsByName();
    for(CountedItem item : items)
    {
      for(int i=0;i<level;i++) System.out.print('\t');
      System.out.println(item);
    }
  }
}
