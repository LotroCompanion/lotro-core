package delta.games.lotro.lore.items.filters;

import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;

/**
 * Filter items that can go in a given slot.
 * @author DAM
 */
public class ItemSlotFilter implements ItemFilter
{
  private EquipmentLocation _location;

  /**
   * Constructor.
   * @param slot Slot to use.
   */
  public ItemSlotFilter(EQUIMENT_SLOT slot)
  {
    _location=slot.getLocation();
  }

  /**
   * Constructor.
   * @param location Location to use.
   */
  public ItemSlotFilter(EquipmentLocation location)
  {
    _location=location;
  }

  public boolean accept(Item item)
  {
    EquipmentLocation location=item.getEquipmentLocation();
    if (_location==location)
    {
      return true;
    }
    if (_location==EquipmentLocation.OFF_HAND)
    {
      // If off-hand, allow main hand weapons
      if (item instanceof Weapon)
      {
        return location==EquipmentLocation.MAIN_HAND;
      }
    }
    return false;
  }
}
