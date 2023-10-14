package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.DamageType;

/**
 * Reactive vital effect.
 * @author DAM
 */
public class ReactiveVitalEffect extends PropertyModificationEffect
{
  // Incoming damage types
  private List<DamageType> _damageTypes;
  // Damage type override: type of damage received by the attacker (reflect), if different from source damage type
  // Usually <code>null</code>.
  private DamageType _attackerDamageTypeOverride;
  // Attacker reactive vital (may be <code>null</code>).
  private ReactiveVitalChange _attacker;
  // Attacker effect (may be <code>null</code>).
  private EffectAndProbability _attackerEffect;
  // Defender reactive vital (may be <code>null</code>).
  private ReactiveVitalChange _defender;
  // Defender effect (may be <code>null</code>).
  private EffectAndProbability _defenderEffect;
  // Indicates if the effect is removed once it is triggered
  private boolean _removeOnProc;
  // Vital types? Always health/morale?
  //private List<StatDescription> _vitalTypes;

  /**
   * Constructor.
   */
  public ReactiveVitalEffect()
  {
    super();
    _damageTypes=new ArrayList<DamageType>();
    _attackerDamageTypeOverride=null;
    _attacker=null;
    _attackerEffect=null;
    _defender=null;
    _defenderEffect=null;
    _removeOnProc=false;
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
   * Set the attacker reactive vital change.
   * @param change Change to set.
   */
  public void setAttackerReactiveVitalChange(ReactiveVitalChange change)
  {
    _attacker=change;
  }

  /**
   * Get the attacker reactive vital change.
   * @return A reactive vital change.
   */
  public ReactiveVitalChange getAttackerVitalChange()
  {
    return _attacker;
  }

  /**
   * Set the attacker effect.
   * @param effect Effect to set (may be <code>null</code>).
   */
  public void setAttackerEffect(EffectAndProbability effect)
  {
    _attackerEffect=effect;
  }

  /**
   * Get the defender effect.
   * @return an effect+probability (or <code>null</code> if none).
   */
  public EffectAndProbability getAttackerEffect()
  {
    return _attackerEffect;
  }

  /**
   * Set the defender reactive vital change.
   * @param change Change to set.
   */
  public void setDefenderReactiveVitalChange(ReactiveVitalChange change)
  {
    _defender=change;
  }

  /**
   * Get the defender reactive vital change.
   * @return A reactive vital change.
   */
  public ReactiveVitalChange getDefenderVitalChange()
  {
    return _defender;
  }

  /**
   * Set the defender effect.
   * @param effect Effect to set (may be <code>null</code>).
   */
  public void setDefenderEffect(EffectAndProbability effect)
  {
    _defenderEffect=effect;
  }

  /**
   * Get the defender effect.
   * @return an effect+probability (or <code>null</code> if none).
   */
  public EffectAndProbability getDefenderEffect()
  {
    return _defenderEffect;
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
}
