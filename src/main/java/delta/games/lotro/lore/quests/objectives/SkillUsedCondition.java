  package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Skill used condition.
 * @author DAM
 */
public class SkillUsedCondition extends ObjectiveCondition
{
  private SkillDescription _skill;
  private Integer _maxPerDay;

  /**
   * Constructor.
   */
  public SkillUsedCondition()
  {
    _skill=null;
    _maxPerDay=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.SKILL_USED;
  }

  /**
   * Get the targeted skill.
   * @return a skill or <code>null</code>.
   */
  public SkillDescription getSkill()
  {
    return _skill;
  }

  /**
   * Set the targeted skill.
   * @param skill the skill to set (may be <code>null</code>).
   */
  public void setSkill(SkillDescription skill)
  {
    _skill=skill;
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
    int count=getCount();
    if (count>1)
    {
      sb.append(count).append(" x");
    }
    if (_maxPerDay!=null)
    {
      sb.append(" (max per day: ").append(_maxPerDay).append(')');
    }
    return sb.toString();
  }
}
