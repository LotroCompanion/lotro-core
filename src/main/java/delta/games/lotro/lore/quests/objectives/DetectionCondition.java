package delta.games.lotro.lore.quests.objectives;

/**
 * Base class for 'Detection' conditions.
 * @author DAM
 */
public abstract class DetectionCondition extends ObjectiveCondition
{
  private ConditionTarget _target;

  /**
   * Constructor.
   */
  public DetectionCondition()
  {
    _target=null;
  }

  /**
   * Get the action.
   * @return a displayable action.
   */
  public abstract String getAction();

  /**
   * Get the target.
   * @return a target or <code>null</code>.
   */
  public ConditionTarget getTarget()
  {
    return _target;
  }

  /**
   * Set the target.
   * @param target the target to set (may be <code>null</code>).
   */
  public void setTarget(ConditionTarget target)
  {
    _target=target;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    String action=getAction();
    sb.append(": ").append(action).append(' ');
    if (_target!=null)
    {
      sb.append(_target);
    }
    else
    {
      sb.append("???");
    }
    return sb.toString();
  }
}
