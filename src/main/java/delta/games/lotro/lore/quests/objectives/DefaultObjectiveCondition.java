package delta.games.lotro.lore.quests.objectives;

/**
 * Default, simple implementation of an objective condition.
 * @author DAM
 */
public class DefaultObjectiveCondition extends ObjectiveCondition
{
  private ConditionType _type;

  /**
   * Constructor.
   * @param type Condition type.
   */
  public DefaultObjectiveCondition(ConditionType type)
  {
    _type=type;
  }

  @Override
  public ConditionType getType()
  {
    return _type;
  }
}
