package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.lore.items.WeaponInstance;

/**
 * Instance of the legendary weapon.
 * @author DAM
 */
public class LegendaryWeaponInstance extends WeaponInstance
{
  // Legendary attributes.
  private LegendaryAttrs _attrs;

  /**
   * Get the legendary attributes.
   * @return the legendary attributes.
   */
  public LegendaryAttrs getLegendaryAttributes()
  {
    return _attrs;
  }

  /**
   * Set the legendary attributes.
   * @param attrs Attributes to set.
   */
  public void setLegendaryAttributes(LegendaryAttrs attrs)
  {
    _attrs=attrs;
  }
}
