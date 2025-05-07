package delta.games.lotro.common.requirements;

import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;

/**
 * World event requirement.
 * @author DAM
 */
public class WorldEventRequirement implements Requirement
{
  private SimpleWorldEventCondition _condition;

  /**
   * Constructor.
   * @param condition Condition.
   */
  public WorldEventRequirement(SimpleWorldEventCondition condition)
  {
    _condition=condition;
  }

  /**
   * Get the managed condition.
   * @return the managed condition.
   */
  public SimpleWorldEventCondition getCondition()
  {
    return _condition;
  }
}
