package delta.games.lotro.lore.quests.objectives;

/**
 * 'Detecting' condition.
 * @author DAM
 */
public class DetectingCondition extends DetectionCondition
{
  @Override
  public String getAction()
  {
    return "Detect ";
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.DETECTING;
  }
}
