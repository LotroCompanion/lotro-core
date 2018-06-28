package delta.games.lotro.lore.items.filters;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.CharacterProficiencies;
import delta.games.lotro.common.CharacterClass;
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

  /**
   * Constructor.
   * @param characterClass Character class.
   * @param level Character level.
   */
  public CharacterProficienciesFilter(CharacterClass characterClass, int level)
  {
    _weaponTypes=CharacterProficiencies.getWeaponProficiencies(characterClass,level);
    _armourTypes=CharacterProficiencies.getArmourProficiencies(characterClass,level);
    _enabled=true;
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

  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    if (item instanceof Weapon)
    {
      Weapon weapon=(Weapon)item;
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
