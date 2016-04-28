package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can go in a given slot.
 * @author DAM
 */
public class ItemSlotFilter implements ItemFilter
{
  private EquipmentLocation _location;

  /**
   * Constructor.
   * @param location Slot to use.
   */
  public ItemSlotFilter(EquipmentLocation location)
  {
    _location=location;
  }

  public boolean accept(Item item)
  {
    EquipmentLocation location=item.getEquipmentLocation();
    return _location==location;
  }
}
