package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.Weapon;

/**
 * Legendary weapon description.
 * @author DAM
 */
public class LegendaryWeapon extends Weapon implements Legendary
{
  /**
   * Constructor.
   */
  public LegendaryWeapon()
  {
    super();
    setCategory(ItemCategory.LEGENDARY_WEAPON);
  }

  /**
   * Copy constructor.
   * @param weapon Source weapon.
   */
  public LegendaryWeapon(LegendaryWeapon weapon)
  {
    super(weapon);
    setCategory(ItemCategory.LEGENDARY_WEAPON);
  }
}
