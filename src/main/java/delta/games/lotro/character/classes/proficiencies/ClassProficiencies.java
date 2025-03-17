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
  private Integer _minLevelForDualWield;
  private ArmourType _armourTypeForMitigations;

  /**
   * Constructor.
   */
  public ClassProficiencies()
  {
    _weaponProficiencies=new TypedClassProficiencies<WeaponType>();
    _armourProficiencies=new TypedClassProficiencies<ArmourType>();
    _minLevelForDualWield=null;
    _armourTypeForMitigations=null;
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

  /**
   * Get the armour type for mitigations.
   * @return the armour type for mitigations.
   */
  public ArmourType getArmourTypeForMitigations()
  {
    return _armourTypeForMitigations;
  }

  /**
   * Set the armour type for mitigations.
   * @param armourTypeForMitigations Armour type to set.
   */
  public void setArmourTypeForMitigations(ArmourType armourTypeForMitigations)
  {
    _armourTypeForMitigations=armourTypeForMitigations;
  }

  /**
   * Get the minimum character level to allow dual-wield.
   * @return An integer value or <code>null</code> if it is never allowed.
   */
  public Integer getMinLevelForDualWield()
  {
    return _minLevelForDualWield;
  }

  /**
   * Set the minimum level for dual-wield.
   * @param minLevelForDualWield Level to set (<code>null</code> to forbid dual-wield).
   */
  public void setMinLevelForDualWield(Integer minLevelForDualWield)
  {
    _minLevelForDualWield=minLevelForDualWield;
  }
}
