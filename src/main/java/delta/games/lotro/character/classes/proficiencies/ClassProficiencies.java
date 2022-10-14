package delta.games.lotro.character.classes.proficiencies;

import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Proficiencies for a single class.
 * @author DAM
 */
public class ClassProficiencies
{
  private TypedClassProficiencies<WeaponType> _weaponProficiencies;
  private TypedClassProficiencies<ArmourType> _armourProficiencies;

  /**
   * Constructor.
   */
  public ClassProficiencies()
  {
    _weaponProficiencies=new TypedClassProficiencies<WeaponType>();
    _armourProficiencies=new TypedClassProficiencies<ArmourType>();
  }

  /**
   * Get the weapon proficiencies.
   * @return the weapon proficiencies.
   */
  public TypedClassProficiencies<WeaponType> getWeaponProficiencies()
  {
    return _weaponProficiencies;
  }

  /**
   * Get the armour proficiencies.
   * @return the armour proficiencies.
   */
  public TypedClassProficiencies<ArmourType> getArmourProficiencies()
  {
    return _armourProficiencies;
  }
}
