package delta.games.lotro.character.storage.stash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.comparators.ItemStashIdComparator;

/**
 * Stash for items.
 * @author DAM
 */
public class ItemsStash
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ItemsStash.class);

  private int _nextId;
  private HashMap<Integer,ItemInstance<? extends Item>> _items;
  private List<ItemInstance<? extends Item>> _itemsList;

  /**
   * Constructor.
   */
  public ItemsStash()
  {
    _nextId=1;
    _items=new HashMap<Integer,ItemInstance<? extends Item>>();
    _itemsList=new ArrayList<ItemInstance<? extends Item>>();
  }

  /**
   * Get the value of the next ID.
   * @return the value of the next ID.
   */
  public int getNextId() {
    return _nextId;
  }

  /**
   * Set the next ID.
   * @param nextId Value to set.
   */
  public void setNextId(int nextId) {
    _nextId=nextId;
  }

  /**
   * Get the internal list of items.
   * @return a list of items.
   */
  public List<ItemInstance<? extends Item>> getItemsList()
  {
    return _itemsList;
  }

  /**
   * Register an item.
   * @param item Item to register.
   */
  public void registerItem(ItemInstance<? extends Item> item)
  {
    Integer stashId=item.getStashIdentifier();
    _items.put(stashId,item);
    _itemsList.add(item);
  }

  /**
   * Add an item in this stash.
   * @param item Item to add.
   */
  public void addItem(ItemInstance<? extends Item> item)
  {
    Integer stashId=item.getStashIdentifier();
    if (stashId!=null)
    {
      ItemInstance<? extends Item> old=_items.get(stashId);
      if (old!=null)
      {
        if (item!=old)
        {
          LOGGER.warn("Trying to replace a stash item. Ignored.");
        }
        return;
      }
    }
    else
    {
      stashId=Integer.valueOf(_nextId);
      _nextId++;
      item.setStashIdentifier(stashId);
    }
    registerItem(item);
  }

  /**
   * Get an item using its stash identifier.
   * @param stashId Stash identifier.
   * @return An item or <code>null</code> if not found.
   */
  public ItemInstance<? extends Item> getItemById(int stashId)
  {
    return _items.get(Integer.valueOf(stashId));
  }

  /**
   * Remove an item using its stash identifier.
   * @param stashId Stash identifier.
   */
  public void removeItem(Integer stashId)
  {
    ItemInstance<? extends Item> old=_items.remove(stashId);
    if (old!=null)
    {
      old.setStashIdentifier(null);
      _itemsList.remove(old);
    }
  }

  /**
   * Remove all items from this stash.
   */
  public void removeAll()
  {
    _nextId=1;
    for(ItemInstance<? extends Item> item : _items.values())
    {
      item.setStashIdentifier(null);
    }
    clear();
  }

  /**
   * Clear contents.
   */
  public void clear()
  {
    _items.clear();
    _itemsList.clear();
  }

  /**
   * Get all items in this stash.
   * @return A possibly empty but not <code>null</code> list of items,
   * sorted by stash identifier.
   */
  public List<ItemInstance<? extends Item>> getAll()
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>(_itemsList);
    Collections.sort(ret,new ItemStashIdComparator());
    return ret;
  }

  /**
   * Dump contents to a string.
   * @param level Indentation level.
   * @return a displayable contents.
   */
  public String dump(int level)
  {
    StringBuilder sb=new StringBuilder();
    List<ItemInstance<? extends Item>> items=getAll();
    for(ItemInstance<? extends Item> item : items)
    {
      for(int i=0;i<level;i++) sb.append('\t');
      sb.append(item).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
