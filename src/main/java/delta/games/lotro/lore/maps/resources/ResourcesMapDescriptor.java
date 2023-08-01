package delta.games.lotro.lore.maps.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.items.Item;

/**
 * Descriptor for a resources map.
 * @author DAM
 */
public class ResourcesMapDescriptor
{
  private CraftingLevel _level;
  private Map<Integer,Item> _items;
  private SortedSet<Integer> _mapIds;

  /**
   * Constructor.
   * @param level Crafting level (profession+tier).
   */
  public ResourcesMapDescriptor(CraftingLevel level)
  {
    _level=level;
    _items=new HashMap<Integer,Item>();
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
  public List<Item> getItems()
  {
    List<Item> items=new ArrayList<Item>(_items.values());
    Comparator<Item> c=new IdentifiableComparator<Item>();
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
  public void addItem(Item item)
  {
    _items.put(Integer.valueOf(item.getIdentifier()),item);
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
   * Add a map identifier.
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
