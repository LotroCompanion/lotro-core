package delta.games.lotro.lore.worldEvents;

/**
 * Simple boolean world event.
 * @author DAM
 */
public class BooleanWorldEvent extends AbstractBooleanWorldEvent
{
  private boolean _defaultValue;

  /**
   * Constructor.
   */
  public BooleanWorldEvent()
  {
    _defaultValue=false;
  }

  /**
   * Get the default value.
   * @return A default value.
   */
  public boolean getDefaultValue()
  {
    return _defaultValue;
  }

  /**
   * Set the default value.
   * @param defaultValue Default value to set.
   */
  public void setDefaultValue(boolean defaultValue)
  {
    _defaultValue=defaultValue;
  }
}
