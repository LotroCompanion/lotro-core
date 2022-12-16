package delta.games.lotro.character.storage.wallet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.status.StatusMetadata;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Wallet.
 * @author DAM
 */
public class Wallet
{
  private HashMap<Integer,CountedItem<Item>> _items;
  private StatusMetadata _statusMetadata;

  /**
   * Constructor.
   */
  public Wallet()
  {
    _items=new HashMap<Integer,CountedItem<Item>>();
    _statusMetadata=new StatusMetadata();

  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(CountedItem<Item> item)
  {
    int id=item.getId();
    _items.put(Integer.valueOf(id),item);
  }

  /**
   * Get an item by identifier.
   * @param itemId Identifier of the item to get.
   * @return An item or <code>null</code> if not found.
   */
  public CountedItem<Item> getById(int itemId)
  {
    for(CountedItem<Item> item : _items.values())
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
   * Get all items in this wallet, sorted by ID.
   * @return A list of items.
   */
  public List<CountedItem<Item>> getAllItemsSortedByID()
  {
    List<Integer> ids=new ArrayList<Integer>(_items.keySet());
    Collections.sort(ids);
    List<CountedItem<Item>> ret=new ArrayList<CountedItem<Item>>();
    for(Integer id : ids)
    {
      ret.add(_items.get(id));
    }
    return ret;
  }

  /**
   * Get all items in this wallet, sorted by name.
   * @return A list of items.
   */
  public List<CountedItem<Item>> getAllItemsSortedByName()
  {
    List<CountedItem<Item>> ret=new ArrayList<CountedItem<Item>>(_items.values());
    ItemNameComparator nameComparator=new ItemNameComparator();
    DataProvider<CountedItem<Item>,Item> provider=new DataProvider<CountedItem<Item>,Item>()
    {
      @Override
      public Item getData(CountedItem<Item> p)
      {
        return p.getItem();
      }
    };
    DelegatingComparator<CountedItem<Item>,Item> c=new DelegatingComparator<CountedItem<Item>,Item>(provider,nameComparator);
    Collections.sort(ret,c);
    return ret;
  }

  /**
   * Get the status metadata.
   * @return the status metadata.
   */
  public StatusMetadata getStatusMetadata()
  {
    return _statusMetadata;
  }

  /**
   * Dump contents.
   * @param level Indentation level.
   */
  public void dump(int level)
  {
    List<CountedItem<Item>> items=getAllItemsSortedByID();
    for(CountedItem<Item> item : items)
    {
      for(int i=0;i<level;i++) System.out.print('\t');
      System.out.println(item);
    }
  }
}
