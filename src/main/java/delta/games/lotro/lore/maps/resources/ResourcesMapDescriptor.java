package delta.games.lotro.lore.maps.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Descriptor for a resources map.
 * @author DAM
 */
public class ResourcesMapDescriptor
{
  private CraftingLevel _level;
  private Map<Integer,Proxy<Item>> _items;
  private SortedSet<Integer> _mapIds;

  /**
   * Constructor.
   * @param level Crafting level (profession+tier).
   */
  public ResourcesMapDescriptor(CraftingLevel level)
  {
    _level=level;
    _items=new HashMap<Integer,Proxy<Item>>();
    _mapIds=new TreeSet<Integer>();
  }

  /**
   * Get the managed crafting level.
   * @return the managed crafting level.
   */
  public CraftingLevel getLevel()
  {
    return _level;
  }

  /**
   * Get the managed resource items.
   * @return a list of resource items.
   */
  public List<Proxy<Item>> getItems()
  {
    List<Proxy<Item>> items=new ArrayList<Proxy<Item>>(_items.values());
    DataProvider<Proxy<Item>,Item> provider=new DataProvider<Proxy<Item>,Item>()
    {
      public Item getData(Proxy<Item> p)
      {
        return p.getObject();
      }
    };
    Comparator<Item> itemC=new ItemIdComparator();
    Comparator<Proxy<Item>> c=new DelegatingComparator<Proxy<Item>,Item>(provider,itemC);
    Collections.sort(items,c);
    return items;
  }

  /**
   * Indicates if this map descriptor uses the given item.
   * @param itemId Item identifier.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasItem(int itemId)
  {
    return _items.get(Integer.valueOf(itemId))!=null;
  }
  /**
   * Add a resource item.
   * @param item Item to add.
   */
  public void addItem(Proxy<Item> item)
  {
    _items.put(Integer.valueOf(item.getId()),item);
  }

  /**
   * Get the identifiers of the involved maps.
   * @return A list of map identifiers (dungeon or parchment map).
   */
  public List<Integer> getMapIds()
  {
    return new ArrayList<Integer>(_mapIds);
  }

  /**
   * Add a map identifieer.
   * @param mapId Map identifier to add. 
   */
  public void addMapId(int mapId)
  {
    _mapIds.add(Integer.valueOf(mapId));
  }

  public String toString()
  {
    return "Resources map for: "+_level.toString();
  }
}
