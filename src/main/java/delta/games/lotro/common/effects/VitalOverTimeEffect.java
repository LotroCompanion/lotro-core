package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;

/**
 * Vital over-time effect.
 * @author DAM
 */
public class VitalOverTimeEffect implements EffectAspect
{
  // Morale, Power, ...
  private StatDescription _stat;
  // Damage type (if harmful, null otherwise)
  private DamageType _damageType;
  // Initial change
  private VitalChangeDescription _initialChange;
  // Over-time change
  private VitalChangeDescription _overTimeChange;

  /**
   * Constructor.
   */
  public VitalOverTimeEffect()
  {
    _stat=WellKnownStat.MORALE;
    _damageType=null;
    _initialChange=null;
    _overTimeChange=null;
  }

  /**
   * Get the involved stat.
   * @return the involved stat.
   */
  public StatDescription getStat()
  {
    return _stat;
  }

  /**
   * Set the involved stat.
   * @param stat Stat to set.
   */
  public void setStat(StatDescription stat)
  {
    _stat=stat;
  }

  /**
   * Get the damage type.
   * @return the damage type.
   */
  public DamageType getDamageType()
  {
    return _damageType;
  }

  /**
   * Set the damage type.
   * @param damageType Damage type to set.
   */
  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  /**
   * Get the initial change description.
   * @return the initial change description.
   */
  public VitalChangeDescription getInitialChangeDescription()
  {
    return _initialChange;
  }

  /**
   * Set the initial change.
   * @param initialChange Initial change to set (may be <code>null</code>).
   */
  public void setInitialChangeDescription(VitalChangeDescription initialChange)
  {
    _initialChange=initialChange;
  }

  /**
   * Get the over-time change description.
   * @return the over-time change description.
   */
  public VitalChangeDescription getOverTimeChangeDescription()
  {
    return _overTimeChange;
  }

  /**
   * Set the over-time change.
   * @param overTimeChange Initial change to set (may be <code>null</code>).
   */
  public void setOverTimeChangeDescription(VitalChangeDescription overTimeChange)
  {
    _overTimeChange=overTimeChange;
  }
}
