package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Filter weapons with a given weapon type.
 * @author DAM
 */
public class WeaponTypeFilter implements ItemFilter
{
  private WeaponType _type;

  /**
   * Constructor.
   * @param type Type to search.
   */
  public WeaponTypeFilter(WeaponType type)
  {
    _type=type;
  }

  /**
   * Get the type to select.
   * @return A type or <code>null</code> to select all.
   */
  public WeaponType getWeaponType()
  {
    return _type;
  }

  /**
   * Set the type to search.
   * @param type Type to search.
   */
  public void setWeaponType(WeaponType type)
  {
    _type=type;
  }

  public boolean accept(Item item)
  {
    if (_type!=null)
    {
      if (item instanceof Weapon)
      {
        WeaponType type=((Weapon)item).getWeaponType();
        return type==_type;
      }
      return false;
    }
    return true;
  }
}
