package delta.games.lotro.lore.quests.objectives;

/**
 * Time expired condition.
 * @author DAM
 */
public class TimeExpiredCondition extends ObjectiveCondition
{
  /**
   * Duration (seconds).
   */
  private int _duration;

  /**
   * Constructor.
   */
  public TimeExpiredCondition()
  {
    _duration=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.TIME_EXPIRED;
  }

  /**
   * Get the duration.
   * @return a duration.
   */
  public int getDuration()
  {
    return _duration;
  }

  /**
   * Set the duration.
   * @param duration the duration to set (seconds).
   */
  public void setDuration(int duration)
  {
    _duration=duration;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Timed expired (");
    sb.append(_duration).append("s)");
    return sb.toString();
  }
}
