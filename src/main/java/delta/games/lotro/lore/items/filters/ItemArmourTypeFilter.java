package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items with a given armour type.
 * @author DAM
 */
public class ItemArmourTypeFilter implements ItemFilter
{
  private ArmourType _armourType;

  /**
   * Constructor.
   * @param armourType Armour type to search.
   */
  public ItemArmourTypeFilter(ArmourType armourType)
  {
    _armourType=armourType;
  }

  public boolean accept(Item item)
  {
    if (item instanceof Armour)
    {
      ArmourType armourType=((Armour)item).getArmourType();
      return _armourType==armourType;
    }
    return false;
  }
}
