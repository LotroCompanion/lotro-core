package delta.games.lotro.lore.quests.objectives;

/**
 * 'Enter Detection' condition.
 * @author DAM
 */
public class EnterDetectionCondition extends DetectionCondition
{
  @Override
  public String getAction()
  {
    return "Detect ";
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.ENTER_DETECTION;
  }
}
