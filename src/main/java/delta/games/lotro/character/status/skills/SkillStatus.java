package delta.games.lotro.character.status.skills;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Skill status.
 * @author DAM
 */
public class SkillStatus
{
  private SkillDescription _skill;
  private boolean _available;

  /**
   * Constructor.
   * @param skill Associated skill.
   */
  public SkillStatus(SkillDescription skill)
  {
    if (skill==null)
    {
      throw new IllegalArgumentException("skill is null");
    }
    _skill=skill;
    _available=false;
  }

  /**
   * Get the associated skill.
   * @return the associated skill.
   */
  public SkillDescription getSkill()
  {
    return _skill;
  }

  /**
   * Indicates if this skill is available or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAvailable()
  {
    return _available;
  }

  /**
   * Set the 'available' flag.
   * @param available Value to set.
   */
  public void setAvailable(boolean available)
  {
    _available=available;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int skillID=_skill.getIdentifier();
    sb.append("Skill ").append(_skill.getName()).append(" (").append(skillID).append("): ");
    if (_available)
    {
      sb.append("available");
    }
    else
    {
      sb.append("not available");
    }
    return sb.toString();
  }
}
