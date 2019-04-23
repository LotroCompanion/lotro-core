package delta.games.lotro.lore.quests.objectives;

/**
 * Monster die condition.
 * @author DAM
 */
public class MonsterDiedCondition extends ObjectiveCondition
{
  @Override
  public ConditionType getType()
  {
    return ConditionType.MONSTER_DIED;
  }
}
