package delta.games.lotro.common.effects;

import delta.games.lotro.utils.maths.Progression;

/**
 * Vital change description (initial or over time). 
 * @author DAM
 */
public class VitalChangeDescription
{
  private Float _constant;
  private Progression _progression;
  // TODO
  //private float _variance;
  //private ModPropertyList _valueModifiers;
  //private float _critMultiplier;
  //private ModPropertyList _critMultiplierModifiers;
  private Float _min;
  private Float _max;

  /**
   * Constructor.
   */
  public VitalChangeDescription()
  {
    _constant=null;
    _progression=null;
    _min=null;
    _max=null;
  }

  /**
   * Is a constant change.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isConstant()
  {
    return _constant!=null;
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
   * Is a progression-ruled change.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isProgression()
  {
    return _progression!=null;
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
