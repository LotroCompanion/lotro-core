package delta.games.lotro.character.classes;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Skill for a class.
 * @author DAM
 */
public class ClassSkill
{
  private int _requiredLevel;
  private SkillDescription _skill;

  /**
   * Constructor.
   * @param requiredLevel Required level.
   * @param skill Skill.
   */
  public ClassSkill(int requiredLevel, SkillDescription skill)
  {
    _requiredLevel=requiredLevel;
    _skill=skill;
  }

  /**
   * Get the character level required for this skill.
   * @return a character level.
   */
  public int getRequiredLevel()
  {
    return _requiredLevel;
  }

  /**
   * Get the managed skill.
   * @return a skill.
   */
  public SkillDescription getSkill()
  {
    return _skill;
  }
}
