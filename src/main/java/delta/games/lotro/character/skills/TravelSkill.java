package delta.games.lotro.character.skills;

import delta.games.lotro.common.enums.TravelLink;

/**
 * Travel skill.
 * <p>
 * A skill that gives a travel feature.
 * @author DAM
 */
public class TravelSkill extends SkillDescription
{
  private TravelLink _type;

  /**
   * Constructor.
   * @param type Travel type.
   */
  public TravelSkill(TravelLink type)
  {
    super();
    _type=type;
  }

  /**
   * Get the travel type.
   * @return A travel type.
   */
  public TravelLink getType()
  {
    return _type;
  }
}
