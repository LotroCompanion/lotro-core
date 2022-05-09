package delta.games.lotro.lore.worldEvents;

/**
 * World event with a boolean value computed from a world event condition.
 * @author DAM
 */
public class ConditionWorldEvent extends AbstractBooleanWorldEvent
{
  private AbstractWorldEventCondition _condition;

  /**
   * Constructor.
   */
  public ConditionWorldEvent()
  {
    _condition=null;
  }

  /**
   * Get the managed condition.
   * @return A condition.
   */
  public AbstractWorldEventCondition getCondition()
  {
    return _condition;
  }

  /**
   * Set the managed condition.
   * @param condition Condition to set.
   */
  public void setCondition(AbstractWorldEventCondition condition)
  {
    _condition=condition;
  }
}
