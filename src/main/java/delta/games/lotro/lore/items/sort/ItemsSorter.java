package delta.games.lotro.lore.items.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.EquipmentLocations;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;

/**
 * Sort items.
 * @author DAM
 */
public class ItemsSorter
{
  private static final String WEAPON="weapon";

  private HashMap<String,List<Item>> _items;

  /**
   * Constructor.
   */
  public ItemsSorter()
  {
    _items=new HashMap<String,List<Item>>();
    for(EquipmentLocation slot : EquipmentLocation.getAll())
    {
      _items.put(slot.getKey(),new ArrayList<Item>());
    }
  }

  /**
   * Get items that fit a location.
   * @param location Targeted location.
   * @return A list of items.
   */
  public List<Item> getItems(EquipmentLocation location)
  {
    List<Item> ret=new ArrayList<Item>();
    ret.addAll(_items.get(location.getKey()));
    if (location==EquipmentLocations.OFF_HAND)
    {
      ret.addAll(_items.get(WEAPON));
    }
    return ret;
  }

  /**
   * Sort items.
   * @param items Items to sort.
   */
  public void sortItems(Collection<Item> items)
  {
    List<Item> weapons=new ArrayList<Item>();

    for(Item item : items)
    {
      EquipmentLocation location=item.getEquipmentLocation();
      if (location!=null)
      {
        List<Item> list=_items.get(location.getKey());
        list.add(item);
        if (item instanceof Weapon)
        {
          if (location==EquipmentLocations.MAIN_HAND)
          {
            weapons.add(item);
          }
        }
      }
    }
    _items.put(WEAPON,weapons);
  }
}
