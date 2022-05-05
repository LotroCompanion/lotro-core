package delta.games.lotro.lore.worldEvents;

import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.utils.Proxy;

/**
 * World event condition item. 
 * To be used in AND/OR or count condition lists.
 * @author DAM
 */
public class WorldEventConditionItem
{
  private ComparisonOperator _operator;
  private Proxy<WorldEvent> _worldEvent;
  // Value to compare to:
  // - constant value
  private Integer _value;
  // - OR world event value
  private Proxy<WorldEvent> _compareToWorldEvent;

  /**
   * Constructor.
   * @param operator Comparison operator.
   * @param worldEvent World event to assess.
   * @param value Value to compare to.
   */
  public WorldEventConditionItem(ComparisonOperator operator, Proxy<WorldEvent> worldEvent, int value)
  {
    _operator=operator;
    _worldEvent=worldEvent;
    _value=Integer.valueOf(value);
  }

  /**
   * Constructor.
   * @param operator Comparison operator.
   * @param worldEvent World event to assess.
   * @param compareToWorldEvent World event to compare to.
   */
  public WorldEventConditionItem(ComparisonOperator operator, Proxy<WorldEvent> worldEvent, Proxy<WorldEvent> compareToWorldEvent)
  {
    _operator=operator;
    _worldEvent=worldEvent;
    _compareToWorldEvent=compareToWorldEvent;
  }

  /**
   * Get the comparison operator.
   * @return the comparison operator.
   */
  public ComparisonOperator getOperator()
  {
    return _operator;
  }

  /**
   * Get the world event to assess.
   * @return the world event to assess.
   */
  public Proxy<WorldEvent> getWorldEvent()
  {
    return _worldEvent;
  }

  /**
   * Get the value to compare to.
   * @return A value or <code>null</code>.
   */
  public Integer getValue()
  {
    return _value;
  }

  /**
   * Get the world event to use for comparison.
   * @return a world event proxy or <code>null</code>.
   */
  public Proxy<WorldEvent> getCompareToWorldEvent()
  {
    return _compareToWorldEvent;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("WE ").append(_worldEvent.getId());
    sb.append(_operator);
    if (_value!=null)
    {
      sb.append(_value);
    }
    else if (_compareToWorldEvent!=null)
    {
      sb.append("WE").append(_compareToWorldEvent.getId());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
