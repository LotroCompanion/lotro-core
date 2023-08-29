package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.Weapon;

/**
 * Legendary weapon description (reloaded).
 * @author DAM
 */
public class LegendaryWeapon2 extends Weapon implements Legendary2
{
  private LegendaryAttrs2 _attrs;

  /**
   * Constructor.
   */
  public LegendaryWeapon2()
  {
    super();
    _attrs=new LegendaryAttrs2();
  }

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.LEGENDARY_WEAPON2;
  }

  @Override
  public LegendaryAttrs2 getLegendaryAttrs()
  {
    return _attrs;
  }

  @Override
  public boolean isScalable()
  {
    return false;
  }
}
