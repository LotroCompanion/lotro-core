package delta.games.lotro.lore.items.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotUtils;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;

/**
 * Sort items.
 * @author DAM
 */
public class ItemsSorter
{
  private HashMap<GearSlot,List<Item>> _items;

  /**
   * Constructor.
   */
  public ItemsSorter()
  {
    _items=new HashMap<GearSlot,List<Item>>();
    for(GearSlot slot : LotroEnumsRegistry.getInstance().get(GearSlot.class).getAll())
    {
      _items.put(slot,new ArrayList<Item>());
    }
  }

  /**
   * Get items that fit a slot.
   * @param slot Targeted slot.
   * @return A list of items.
   */
  public List<Item> getItems(GearSlot slot)
  {
    List<Item> ret=new ArrayList<Item>();
    ret.addAll(_items.get(slot));
    return ret;
  }

  /**
   * Sort items.
   * @param items Items to sort.
   */
  public void sortItems(Collection<Item> items)
  {
    for(Item item : items)
    {
      EquipmentLocation location=item.getEquipmentLocation();
      if (location!=null)
      {
        GearSlot[] slots=GearSlotUtils.getSlots(location);
        if (slots!=null)
        {
          for(GearSlot slot : slots)
          {
            List<Item> list=_items.get(slot);
            list.add(item);
          }
        }
      }
    }
  }
}
