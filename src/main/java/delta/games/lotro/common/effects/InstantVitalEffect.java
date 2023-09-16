package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * Instant vital effect.
 * @author DAM
 */
public class InstantVitalEffect implements EffectAspect
{
  // Morale, Power, ...
  private StatDescription _stat;
  private boolean _multiplicative;
  private VitalChangeDescription _instantChange;

  /**
   * Constructor.
   */
  public InstantVitalEffect()
  {
    _stat=WellKnownStat.MORALE;
    _multiplicative=false;
    _instantChange=null;
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
   * Indicates if this is a multiplicative change or not (addition change).
   * @return <code>true</code> for a multiplicative change.
   */
  public boolean isMultiplicative()
  {
    return _multiplicative;
  }

  /**
   * Set the 'multiplicative' flag.
   * @param multiplicative Multiplicative flag value.
   */
  public void setMultiplicative(boolean multiplicative)
  {
    _multiplicative=multiplicative;
  }

  /**
   * Get the instant change description.
   * @return the instant change description.
   */
  public VitalChangeDescription getInstantChangeDescription()
  {
    return _instantChange;
  }

  /**
   * Set the instant change description.
   * @param instantChange Change to set.
   */
  public void setInstantChangeDescription(VitalChangeDescription instantChange)
  {
    _instantChange=instantChange;
  }
}
