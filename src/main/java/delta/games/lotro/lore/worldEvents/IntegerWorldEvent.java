package delta.games.lotro.lore.worldEvents;

/**
 * Simple integer world event.
 * @author DAM
 */
public class IntegerWorldEvent extends AbstractIntegerWorldEvent
{
  private Integer _defaultValue;
  private Integer _minValue;
  private Integer _maxValue;

  /**
   * Constructor.
   */
  public IntegerWorldEvent()
  {
    _defaultValue=null;
    _minValue=null;
    _maxValue=null;
  }

  /**
   * Get the default value.
   * @return the default value or <code>null</code>.
   */
  public Integer getDefaultValue()
  {
    return _defaultValue;
  }

  /**
   * Set the default value.
   * @param defaultValue the default value to set.
   */
  public void setDefaultValue(Integer defaultValue)
  {
    _defaultValue=defaultValue;
  }

  /**
   * Get the minimum value.
   * @return the minimum value or <code>null</code>.
   */
  public Integer getMinValue()
  {
    return _minValue;
  }

  /**
   * Set the minimum value.
   * @param minValue the minimum value to set.
   */
  public void setMinValue(Integer minValue)
  {
    _minValue=minValue;
  }

  /**
   * Get the maximum value.
   * @return the maximum value or <code>null</code>.
   */
  public Integer getMaxValue()
  {
    return _maxValue;
  }

  /**
   * Set the maximum value.
   * @param maxValue the maximum value to set.
   */
  public void setMaxValue(Integer maxValue)
  {
    _maxValue=maxValue;
  }
}
