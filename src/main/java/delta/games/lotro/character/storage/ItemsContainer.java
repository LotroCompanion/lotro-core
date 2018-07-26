package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Items container.
 * @author DAM
 */
public class ItemsContainer
{
  private HashMap<String,StoredItem> _items;

  /**
   * Constructor.
   */
  public ItemsContainer()
  {
    _items=new HashMap<String,StoredItem>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(StoredItem item)
  {
    String name=item.getName();
    StoredItem old=_items.put(name,item);
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
  public StoredItem getByName(String name)
  {
    return _items.get(name);
  }

  /**
   * Get all items in this wallet, sorted by name.
   * @return A list of items.
   */
  public List<StoredItem> getAllItemsByName()
  {
    List<String> names=new ArrayList<String>(_items.keySet());
    Collections.sort(names);
    List<StoredItem> ret=new ArrayList<StoredItem>();
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
    List<StoredItem> items=getAllItemsByName();
    for(StoredItem item : items)
    {
      for(int i=0;i<level;i++) System.out.print('\t');
      System.out.println(item);
    }
  }
}
