package delta.games.lotro.common.effects;

/**
 * Vital change description (initial or over time). 
 * @author DAM
 */
public class VitalChangeDescription extends AbstractVitalChange
{
  private Float _min;
  private Float _max;

  /**
   * Constructor.
   */
  public VitalChangeDescription()
  {
    super();
    _min=null;
    _max=null;
  }

  /**
   * Is a random change within a range.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRandom()
  {
    return ((_min!=null) && (_max!=null));
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
