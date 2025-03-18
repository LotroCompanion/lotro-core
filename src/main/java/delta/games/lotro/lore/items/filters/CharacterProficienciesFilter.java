package delta.games.lotro.lore.items.filters;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.CharacterProficiencies;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlots;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Filter items based on character proficiencies.
 * @author DAM
 */
public class CharacterProficienciesFilter implements Filter<Item>
{
  private boolean _enabled;
  private Set<WeaponType> _weaponTypes;
  private Set<ArmourType> _armourTypes;
  private boolean _dualWield;
  private GearSlot _slot;

  /**
   * Constructor.
   * @param characterClass Character class.
   * @param level Character level.
   */
  public CharacterProficienciesFilter(ClassDescription characterClass, int level)
  {
    _weaponTypes=CharacterProficiencies.getWeaponProficiencies(characterClass,level);
    _armourTypes=CharacterProficiencies.getArmourProficiencies(characterClass,level);
    _enabled=true;
    _dualWield=CharacterProficiencies.isDualWielding(characterClass,level);
    _slot=null;
  }

  /**
   * Indicates if this filter is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Enable this filter or not.
   * @param enabled <code>true</code> to enable filtering, <code>false</code> to disable filtering.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
  }

  /**
   * Set the slot to use.
   * @param slot Slot to use (may be <code>null</code>).
   */
  public void setSlot(GearSlot slot)
  {
    _slot=slot;
  }

  @Override
  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    if (item instanceof Weapon)
    {
      Weapon weapon=(Weapon)item;
      if (_slot==GearSlots.OTHER_MELEE)
      {
        if (!_dualWield)
        {
          return false;
        }
      }
      WeaponType weaponType=weapon.getWeaponType();
      return ((weaponType==null) || (_weaponTypes.contains(weaponType)));
    }
    if (item instanceof Armour)
    {
      Armour armour=(Armour)item;
      ArmourType armourType=armour.getArmourType();
      return ((armourType==null) || (_armourTypes.contains(armourType)));
    }
    return true;
  }
}
