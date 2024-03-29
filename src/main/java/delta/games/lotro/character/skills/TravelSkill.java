package delta.games.lotro.character.skills;

import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.Position;

/**
 * Travel skill.
 * <p>
 * A skill that gives a travel feature.
 * @author DAM
 */
public class TravelSkill extends SkillDescription
{
  private TravelLink _type;
  private Position _position;

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

  /**
   * Get the target position.
   * @return A position or <code>null</code> if none.
   */
  public Position getPosition()
  {
    return _position;
  }

  /**
   * Set the target position.
   * @param position Position to set.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }
}
