package delta.games.lotro.lore.quests.objectives;

/**
 * 'NPC talk' condition.
 * @author DAM
 */
public class NpcTalkCondition extends NpcCondition
{
  /**
   * Constructor.
   */
  public NpcTalkCondition()
  {
    // Nothing
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.NPC_TALK;
  }

  @Override
  public String getAction()
  {
    return "Talk to";
  }
}
