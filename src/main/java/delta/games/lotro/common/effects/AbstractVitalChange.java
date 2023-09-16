package delta.games.lotro.common.effects;

import delta.games.lotro.utils.maths.Progression;

/**
 * Base class for vital changes. 
 * @author DAM
 */
public class AbstractVitalChange
{
  private Float _constant;
  private Progression _progression;
  // TODO
  //private float _variance;
  //private ModPropertyList _valueModifiers;
  //private float _critMultiplier;
  //private ModPropertyList _critMultiplierModifiers;

  /**
   * Constructor.
   */
  public AbstractVitalChange()
  {
    _constant=null;
    _progression=null;
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
}
