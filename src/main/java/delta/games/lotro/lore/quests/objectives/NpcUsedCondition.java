package delta.games.lotro.lore.quests.objectives;

/**
 * 'NPC used' condition.
 * @author DAM
 */
public class NpcUsedCondition extends NpcCondition
{
  /**
   * Constructor.
   */
  public NpcUsedCondition()
  {
    // Nothing
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.NPC_USED;
  }

  @Override
  public String getAction()
  {
    return "Use";
  }
}
