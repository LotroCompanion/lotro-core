package delta.games.lotro.common.effects;

/**
 * Vital over-time effect.
 * @author DAM
 */
public class VitalOverTimeEffect extends BaseVitalEffect
{
  // Initial change
  private VitalChangeDescription _initialChange;
  // Over-time change
  private VitalChangeDescription _overTimeChange;

  /**
   * Constructor.
   */
  public VitalOverTimeEffect()
  {
    super();
    _initialChange=null;
    _overTimeChange=null;
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
