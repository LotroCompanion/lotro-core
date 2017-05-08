package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.comparators.ItemStashIdComparator;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Stash for items.
 * @author DAM
 */
public class ItemsStash
{
  private static final Logger _logger=LotroLoggers.getCharacterLogger();

  private int _nextId;
  private HashMap<Integer,Item> _items;
  private List<Item> _itemsList;

  /**
   * Constructor.
   */
  public ItemsStash()
  {
    _nextId=1;
    _items=new HashMap<Integer,Item>();
    _itemsList=new ArrayList<Item>();
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
  public List<Item> getItemsList()
  {
    return _itemsList;
  }

  /**
   * Register an item.
   * @param item Item to register.
   */
  public void registerItem(Item item)
  {
    Integer stashId=item.getStashIdentifier();
    _items.put(stashId,item);
    _itemsList.add(item);
  }

  /**
   * Add an item in this stash.
   * @param item Item to add.
   */
  public void addItem(Item item)
  {
    Integer stashId=item.getStashIdentifier();
    if (stashId!=null)
    {
      Item old=_items.get(stashId);
      if (old!=null)
      {
        if (item!=old)
        {
          _logger.warn("Trying to replace a stash item. Ignored.");
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
  public Item getItemById(int stashId)
  {
    return _items.get(Integer.valueOf(stashId));
  }

  /**
   * Remove an item using its stash identifier.
   * @param stashId Stash identifier.
   */
  public void removeItem(Integer stashId)
  {
    Item old=_items.remove(stashId);
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
    for(Item item : _items.values())
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
  public List<Item> getAll()
  {
    List<Item> ret=new ArrayList<Item>(_items.values());
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
    List<Item> items=getAll();
    for(Item item : items)
    {
      for(int i=0;i<level;i++) sb.append('\t');
      sb.append(item).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
