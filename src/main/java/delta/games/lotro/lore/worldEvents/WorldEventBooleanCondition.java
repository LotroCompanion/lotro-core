package delta.games.lotro.lore.worldEvents;

import delta.common.utils.collections.filters.Operator;

/**
 * World event condition that uses a boolean logical operator AND/OR.
 * @author DAM
 */
public class WorldEventBooleanCondition extends AbstractWorldEventCondition
{
  private Operator _operator;

  /**
   * Constructor.
   * @param operator Operator.
   */
  public WorldEventBooleanCondition(Operator operator)
  {
    _operator=operator;
  }

  /**
   * @return the operator
   */
  public Operator getOperator()
  {
    return _operator;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_operator).append(' ');
    sb.append(getItems());
    return sb.toString();
  }
}
