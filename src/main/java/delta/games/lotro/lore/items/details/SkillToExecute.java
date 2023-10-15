package delta.games.lotro.lore.items.details;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * 'skill execute' item detail. 
 * @author DAM
 */
public class SkillToExecute extends ItemDetail
{
  private SkillDescription _skill;
  private Integer _level;

  /**
   * Constructor.
   * @param skill Skill to execute.
   * @param level Level of skill execution.
   */
  public SkillToExecute(SkillDescription skill, Integer level)
  {
    _skill=skill;
    _level=level;
  }

  /**
   * Get the managed skill.
   * @return the managed skill.
   */
  public SkillDescription getSkill()
  {
    return _skill;
  }

  /**
   * Get the skill level.
   * @return A level or <code>null</code>.
   */
  public Integer getLevel()
  {
    return _level;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Skill to execute: ID=");
    sb.append(_skill.getIdentifier());
    sb.append(" - ");
    sb.append(_skill.getName());
    if (_level!=null)
    {
      sb.append(" (level ").append(_level).append(')');
    }
    return sb.toString();
  }
}
