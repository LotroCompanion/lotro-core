package delta.games.lotro.character.gear;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Collection of utility methods related to slot preclusion.
 * @author DAM
 */
public class PreclusionUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(PreclusionUtils.class);

  /**
   * Filter items using slot preclusion rules.
   * @param items Items to filter.
   * @param equippedSlots Currently equipped slots.
   * @return the list of selected items.
   */
  public static List<Item> filterItems(List<Item> items, Set<GearSlot> equippedSlots)
  {
    List<Item> ret=new ArrayList<Item>();
    for(Item item : items)
    {
      boolean ok=true;
      EquipmentLocation precludedLocation=item.getPrecludedSlots();
      if (precludedLocation!=null)
      {
        GearSlot[] precludedSlots=GearSlotUtils.getSlots(precludedLocation);
        for(GearSlot precludedSlot : precludedSlots)
        {
          if (equippedSlots.contains(precludedSlot))
          {
            LOGGER.debug("Removed {} because slot {} is precluded!",item,precludedSlot);
            ok=false;
          }
        }
      }
      if (ok)
      {
        ret.add(item);
      }
    }
    return ret;
  }

  /**
   * Filter item instances using slot preclusion rules.
   * @param itemInstances Item instances to filter.
   * @param equippedSlots Currently equipped slots.
   * @return the list of selected item instances.
   */
  public static List<ItemInstance<? extends Item>> filterItemInstances(List<ItemInstance<? extends Item>> itemInstances, Set<GearSlot> equippedSlots)
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>();
    for(ItemInstance<? extends Item> itemInstance : itemInstances)
    {
      boolean ok=true;
      Item item=itemInstance.getItem();
      EquipmentLocation precludedLocation=item.getPrecludedSlots();
      if (precludedLocation!=null)
      {
        GearSlot[] precludedSlots=GearSlotUtils.getSlots(precludedLocation);
        for(GearSlot precludedSlot : precludedSlots)
        {
          if (equippedSlots.contains(precludedSlot))
          {
            LOGGER.debug("Removed {} because slot {} is precluded!",itemInstance,precludedSlot);
            ok=false;
          }
        }
      }
      if (ok)
      {
        ret.add(itemInstance);
      }
    }
    return ret;
  }

  /**
   * Indicates if the given slot is precluded or not.
   * @param equipment Equipment to use.
   * @param targetSlot Slot to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean slotIsPrecluded(CharacterGear equipment, GearSlot targetSlot)
  {
    for(GearSlot slot : GearSlot.getAll())
    {
      GearSlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents==null)
      {
        continue;
      }
      ItemInstance<?> itemInstance=slotContents.getItem();
      if (itemInstance==null)
      {
        continue;
      }
      Item item=itemInstance.getItem();
      EquipmentLocation precluded=item.getPrecludedSlots();
      if (precluded==null)
      {
        continue;
      }
      GearSlot[] precludedSlots=GearSlotUtils.getSlots(precluded);
      for(GearSlot precludedSlot : precludedSlots)
      {
        if (precludedSlot==targetSlot)
        {
          return true;
        }
      }
    }
    return false;
  }
}
