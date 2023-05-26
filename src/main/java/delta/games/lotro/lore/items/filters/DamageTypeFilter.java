package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;

/**
 * Filter weapons with a given damage type.
 * @author DAM
 */
public class DamageTypeFilter implements ItemFilter
{
  private DamageType _type;

  /**
   * Constructor.
   * @param type Type to search.
   */
  public DamageTypeFilter(DamageType type)
  {
    _type=type;
  }

  /**
   * Get the type to select.
   * @return A type or <code>null</code> to select all.
   */
  public DamageType getDamageType()
  {
    return _type;
  }

  /**
   * Set the type to search.
   * @param type Type to search.
   */
  public void setDamageType(DamageType type)
  {
    _type=type;
  }

  public boolean accept(Item item)
  {
    if (_type!=null)
    {
      if (item instanceof Weapon)
      {
        DamageType type=((Weapon)item).getDamageType();
        return type==_type;
      }
      return false;
    }
    return true;
  }
}
