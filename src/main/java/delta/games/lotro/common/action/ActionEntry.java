package delta.games.lotro.common.action;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Action entry (in an actions chain).
 * @author DAM
 */
public class ActionEntry
{
  private SkillDescription _skill;
  private Float _recovery;

  /**
   * Constructor.
   * @param skill Skill of the action.
   */
  public ActionEntry(SkillDescription skill)
  {
    _skill=skill;
  }

  /**
   * Get the associated skill.
   * @return a skill.
   */
  public SkillDescription getSkill()
  {
    return _skill;
  }

  /**
   * Get the recovery (time?).
   * @return the recovery value.
   */
  public Float getRecovery()
  {
    return _recovery;
  }

  /**
   * Set the recovery (time?).
   * @param recovery the recovery value to set.
   */
  public void setRecovery(Float recovery)
  {
    _recovery=recovery;
  }
}
