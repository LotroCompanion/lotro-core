package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
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
    _attrs=new LegendaryAttrs();
  }

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.LEGENDARY_WEAPON;
  }

  @Override
  public LegendaryAttrs getLegendaryAttrs()
  {
    return _attrs;
  }

  @Override
  public int getItemLevelForDPS()
  {
    int ret=_attrs.getMainLegacyBaseRank();
    // TMP workaround until ItemAdvancement_CombatDPSLevel and ItemAdvancement_CombatPropertyModLevel
    // are managed as different attributes
    AbstractClassDescription reqClass=getUsageRequirements().getRequiredClass();
    if (reqClass!=null)
    {
      if ((WellKnownCharacterClassKeys.LORE_MASTER.equals(reqClass.getKey()))||
          (WellKnownCharacterClassKeys.MINSTREL.equals(reqClass.getKey()))||
          (WellKnownCharacterClassKeys.RUNE_KEEPER.equals(reqClass.getKey())))
      {
        return super.getItemLevelForDPS();
      }
    }
    return ret;
  }
}
