package delta.games.lotro.common.effects;

/**
 * Instant vital effect.
 * @author DAM
 */
public class InstantVitalEffect extends BaseVitalEffect
{
  private boolean _multiplicative;
  private VitalChangeDescription _instantChange;
  private Float _initialChangeMultiplier;

  /**
   * Constructor.
   */
  public InstantVitalEffect()
  {
    super();
    _multiplicative=false;
    _instantChange=null;
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

  /**
   * Get the initial change multiplier.
   * @return A multiplier or <code>null</code>.
   */
  public Float getInitialChangeMultiplier()
  {
    return _initialChangeMultiplier;
  }

  /**
   * Set the initial change multiplier.
   * @param initialChangeMultiplier Value to set.
   */
  public void setInitialChangeMultiplier(Float initialChangeMultiplier)
  {
    _initialChangeMultiplier=initialChangeMultiplier;
  }
}
