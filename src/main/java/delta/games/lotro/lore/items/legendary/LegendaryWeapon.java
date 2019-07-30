package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.Weapon;

/**
 * Legendary weapon description.
 * @author DAM
 */
public class LegendaryWeapon extends Weapon implements Legendary
{
  private LegendaryAttrs _attrs;

  /**
   * Constructor.
   */
  public LegendaryWeapon()
  {
    super();
    setCategory(ItemCategory.LEGENDARY_WEAPON);
    _attrs=new LegendaryAttrs();
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

  @Override
  public LegendaryAttrs getLegendaryAttrs()
  {
    return _attrs;
  }
}
