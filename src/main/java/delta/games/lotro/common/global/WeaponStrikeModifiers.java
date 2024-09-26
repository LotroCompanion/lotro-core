package delta.games.lotro.common.global;

import delta.games.lotro.lore.items.WeaponType;

/**
 * Weapon strike modifiers.
 * @author DAM
 */
public class WeaponStrikeModifiers
{
/*
  #1: Combat_Control_WeaponStrikeModifier 
    Combat_Control_EquipmentCategory: 524288 (Dagger[E])
    Combat_Control_SkillCriticalMult_AddMod: 268445672 (Combat_SkillCriticalMult_AddMod_Dagger)
    Combat_Control_SkillSuperCriticalMult_AddMod: 268445673 (Combat_SkillSuperCriticalMult_AddMod_Dagger)
    Combat_Control_WeaponDamageMultiplier: 268445557 (Combat_WeaponDamageMultiplier_Dagger)
 */

  private WeaponType _type;
  private Integer _criticalMultiplierAddMod;
  private Integer _superCriticalMultiplierAddMod;
  private Integer _weaponDamageMultiplier;

  /**
   * Constructor.
   * @param type Weapon type.
   */
  public WeaponStrikeModifiers(WeaponType type)
  {
    _type=type;
  }

  /**
   * Get the weapon type.
   * @return the weapon type.
   */
  public WeaponType getWeaponType()
  {
    return _type;
  }

  /**
   * Get the critical multiplier property ID.
   * @return a property ID or <code>null</code>.
   */
  public Integer getCriticalMultiplierAddMod()
  {
    return _criticalMultiplierAddMod;
  }

  /**
   * Set the critical multiplier property ID.
   * @param criticalMultiplierAddMod the property ID to set.
   */
  public void setCriticalMultiplierAddMod(Integer criticalMultiplierAddMod)
  {
    _criticalMultiplierAddMod=criticalMultiplierAddMod;
  }

  /**
   * Get the supercritical/devastate multiplier property ID.
   * @return a property ID or <code>null</code>.
   */
  public Integer getSuperCriticalMultiplierAddMod()
  {
    return _superCriticalMultiplierAddMod;
  }

  /**
   * Set the supercritical/devastate multiplier property ID.
   * @param superCriticalMultiplierAddMod the property ID to set.
   */
  public void setSuperCriticalMultiplierAddMod(Integer superCriticalMultiplierAddMod)
  {
    _superCriticalMultiplierAddMod=superCriticalMultiplierAddMod;
  }

  /**
   * Get the damage multiplier property ID.
   * @return a property ID or <code>null</code>.
   */
  public Integer getWeaponDamageMultiplier()
  {
    return _weaponDamageMultiplier;
  }

  /**
   * Set the weapon damage multiplier property ID.
   * @param weaponDamageMultiplier the property ID to set.
   */
  public void setWeaponDamageMultiplier(Integer weaponDamageMultiplier)
  {
    _weaponDamageMultiplier=weaponDamageMultiplier;
  }
}
