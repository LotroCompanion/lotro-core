package delta.games.lotro.lore.worldEvents;

/**
 * @author dm
 */
public class IntegerWorldEvent extends WorldEvent
{
  private int _defaultValue;
  private int _minValue;
  private int _maxValue;

  public IntegerWorldEvent()
  {
    super(0,0);
  }
}
