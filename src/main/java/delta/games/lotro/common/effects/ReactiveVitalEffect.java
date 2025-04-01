package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.lore.items.DamageType;

/**
 * Reactive vital effect.
 * @author DAM
 */
public class ReactiveVitalEffect extends PropertyModificationEffect
{
  // Incoming damage types
  private List<DamageType> _damageTypes;
  // Incoming damage types
  private List<DamageQualifier> _damageQualifiers;
  // Damage type override: type of damage received by the attacker (reflect), if different from source damage type
  // Usually <code>null</code>.
  private DamageType _attackerDamageTypeOverride;
  // Attacker reactive change (may be <code>null</code>).
  private ReactiveChange _attacker;
  // Defender reactive change (may be <code>null</code>).
  private ReactiveChange _defender;
  // Indicates if the effect is removed once it is triggered
  private boolean _removeOnProc;
  // Vital types? Always health/morale?
  private List<VitalType> _vitalTypes;

  /**
   * Constructor.
   */
  public ReactiveVitalEffect()
  {
    super();
    _damageTypes=new ArrayList<DamageType>();
    _damageQualifiers=new ArrayList<DamageQualifier>();
    _attackerDamageTypeOverride=null;
    _attacker=null;
    _defender=null;
    _removeOnProc=false;
    _vitalTypes=new ArrayList<VitalType>();
  }

  /**
   * Add a source damage type.
   * @param damageType Damage type.
   */
  public void addDamageType(DamageType damageType)
  {
    _damageTypes.add(damageType);
  }

  /**
   * Get the source damage types.
   * @return a list of damage types.
   */
  public List<DamageType> getDamageTypes()
  {
    return _damageTypes;
  }

  /**
   * Add a source damage qualifier.
   * @param damageQualifier Damage qualifier.
   */
  public void addDamageQualifier(DamageQualifier damageQualifier)
  {
    _damageQualifiers.add(damageQualifier);
  }

  /**
   * Get the source damage qualifiers.
   * @return a list of damage qualifiers.
   */
  public List<DamageQualifier> getDamageQualifiers()
  {
    return _damageQualifiers;
  }

  /**
   * Set the attacker damage type override.
   * @param damageType A damage type (may be <code>null</code>).
   */
  public void setAttackerDamageTypeOverride(DamageType damageType)
  {
    _attackerDamageTypeOverride=damageType;
  }

  /**
   * Get the attacker damage type override.
   * @return A damage type or <code>null</code> if no damage type change.
   */
  public DamageType getAttackerDamageTypeOverride()
  {
    return _attackerDamageTypeOverride;
  }

  /**
   * Set the attacker reactive change.
   * @param attacker Change to set.
   */
  public void setAttackerReactiveChange(ReactiveChange attacker)
  {
    _attacker=attacker;
  }

  /**
   * Get the attacker reactive change.
   * @return A reactive vital change.
   */
  public ReactiveChange getAttackerReactiveChange()
  {
    return _attacker;
  }

  /**
   * Set the defender reactive change.
   * @param defender Change to set.
   */
  public void setDefenderReactiveChange(ReactiveChange defender)
  {
    _defender=defender;
  }

  /**
   * Get the defender reactive change.
   * @return A reactive change.
   */
  public ReactiveChange getDefenderReactiveChange()
  {
    return _defender;
  }

  /**
   * Set the 'remove on proc' flag.
   * @param removeOnProc Flag to set.
   */
  public void setRemoveOnProc(boolean removeOnProc)
  {
    _removeOnProc=removeOnProc;
  }

  /**
   * Indicates if this effect is removed when it is triggered.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRemoveOnProc()
  {
    return _removeOnProc;
  }

  /**
   * Add a vital type.
   * @param vitalType Vital type.
   */
  public void addVitalType(VitalType vitalType)
  {
    _vitalTypes.add(vitalType);
  }

  /**
   * Get the vital types.
   * @return a list of vital types.
   */
  public List<VitalType> getVitalTypes()
  {
    return _vitalTypes;
  }
}
