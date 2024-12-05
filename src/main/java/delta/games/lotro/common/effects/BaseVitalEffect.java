package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;

/**
 * Base vital effect.
 * @author DAM
 */
public abstract class BaseVitalEffect extends Effect
{
  // Morale, Power, ...
  private StatDescription _stat;
  // Damage type (if harmful, null otherwise)
  private DamageType _damageType;

  /**
   * Constructor.
   */
  protected BaseVitalEffect()
  {
    _stat=WellKnownStat.MORALE;
    _damageType=null;
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
}
