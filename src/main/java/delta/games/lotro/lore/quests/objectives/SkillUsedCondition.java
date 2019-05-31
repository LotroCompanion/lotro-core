  package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Skill used condition.
 * @author DAM
 */
public class SkillUsedCondition extends ObjectiveCondition
{
  private Proxy<SkillDescription> _skill;
  private int _count;
  private Integer _maxPerDay;

  /**
   * Constructor.
   */
  public SkillUsedCondition()
  {
    _skill=null;
    _count=1;
    _maxPerDay=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.SKILL_USED;
  }

  /**
   * Get the proxy to the targeted skill.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<SkillDescription> getProxy()
  {
    return _skill;
  }

  /**
   * Set the proxy to the targeted skill.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<SkillDescription> proxy)
  {
    _skill=proxy;
  }

  /**
   * Get the count.
   * @return a count.
   */
  public int getCount()
  {
    return _count;
  }

  /**
   * Set the count.
   * @param count the count to set.
   */
  public void setCount(int count)
  {
    _count=count;
  }

  /**
   * Get the max skill usage per day.
   * @return a count or <code>null</code> if no limit.
   */
  public Integer getMaxPerDay()
  {
    return _maxPerDay;
  }

  /**
   * Set the max skill usage per day.
   * @param maxPerDay the count to set (<code>null</code> for no limit).
   */
  public void setMaxPerDay(Integer maxPerDay)
  {
    _maxPerDay=maxPerDay;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": ");
    if (_skill!=null)
    {
      sb.append("Use skill: ").append(_skill);
    }
    else
    {
      sb.append("Use ???");
    }
    if (_count>1)
    {
      sb.append(_count).append(" x");
    }
    if (_maxPerDay!=null)
    {
      sb.append(" (max per day: ").append(_maxPerDay).append(')');
    }
    return sb.toString();
  }
}
