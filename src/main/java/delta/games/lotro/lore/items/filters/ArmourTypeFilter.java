package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.Item;

/**
 * Filter armours/shields with a given armour type.
 * @author DAM
 */
public class ArmourTypeFilter implements ItemFilter
{
  private ArmourType _type;

  /**
   * Constructor.
   * @param type Type to search.
   */
  public ArmourTypeFilter(ArmourType type)
  {
    _type=type;
  }

  /**
   * Get the type to select.
   * @return A type or <code>null</code> to select all.
   */
  public ArmourType getArmourType()
  {
    return _type;
  }

  /**
   * Set the type to search.
   * @param type Type to search.
   */
  public void setArmourType(ArmourType type)
  {
    _type=type;
  }

  public boolean accept(Item item)
  {
    if (_type!=null)
    {
      if (item instanceof Armour)
      {
        ArmourType type=((Armour)item).getArmourType();
        return (type==null) || (type==_type);
      }
      return true;
    }
    return true;
  }
}
