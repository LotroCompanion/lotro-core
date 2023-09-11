package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.utils.maths.Progression;

/**
 * Vital instant change effect. 
 * @author DAM
 */
public class VitalInstantChangeEffect implements EffectAspect
{
  // Morale, Power, ...
  private StatDescription _stat;
  private boolean _multiplicative;
  // Initial change: constant, progression or min-max range:
  private Float _constant;
  private Progression _progression;
  private Float _min;
  private Float _max;

  /**
   * Constructor.
   */
  public VitalInstantChangeEffect()
  {
    _stat=WellKnownStat.MORALE;
    _multiplicative=false;
    _constant=null;
    _progression=null;
    _min=null;
    _max=null;
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
   * Get the constant change value.
   * @return A value or <code>null</code> if not constant.
   */
  public Float getConstant()
  {
    return _constant;
  }

  /**
   * Set the constant change value.
   * @param constant Value to set.
   */
  public void setConstant(float constant)
  {
    _constant=Float.valueOf(constant);
  }

  /**
   * Get the change progression.
   * @return A progression or <code>null</code>.
   */
  public Progression getProgression()
  {
    return _progression;
  }

  /**
   * Set the change progression.
   * @param progression Progression change.
   */
  public void setProgression(Progression progression)
  {
    _progression=progression;
  }

  /**
   * Get the minimum change progression.
   * @return A minimum value or <code>null</code>.
   */
  public Float getMinValue()
  {
    return _min;
  }

  /**
   * Set the minimum change value.
   * @param minValue Value to set.
   */
  public void setMinValue(float minValue)
  {
    _min=Float.valueOf(minValue);
  }

  /**
   * Get the maximum change progression.
   * @return A maximum value or <code>null</code>.
   */
  public Float getMaxValue()
  {
    return _max;
  }

  /**
   * Set the maximum change value.
   * @param maxValue Value to set.
   */
  public void setMaxValue(float maxValue)
  {
    _max=Float.valueOf(maxValue);
  }
}
