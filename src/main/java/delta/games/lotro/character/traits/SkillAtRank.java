package delta.games.lotro.character.traits;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Gathers an effect and a rank.
 * @author DAM
 */
public class SkillAtRank
{
  private SkillDescription _skill;
  private int _rank;

  /**
   * Constructor.
   * @param skill Skill.
   * @param rank Rank.
   */
  public SkillAtRank(SkillDescription skill, int rank)
  {
    _skill=skill;
    _rank=rank;
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
   * Get the involved rank.
   * @return a rank.
   */
  public int getRank()
  {
    return _rank;
  }
}
