package delta.games.lotro.character.status.travels;

import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.ExtendedPosition;

/**
 * Status of a single anchor (milestone or hunter camp fire) for a single character.
 * @author DAM
 */
public class AnchorStatus
{
  private TravelLink _type;
  private String _name;
  private ExtendedPosition _position;

  /**
   * Constructor.
   * @param type Type.
   */
  public AnchorStatus(TravelLink type)
  {
    _type=type;
  }

  /**
   * Get the type.
   * @return A type.
   */
  public TravelLink getType()
  {
    return _type;
  }

  /**
   * Get the name.
   * @return a name or <code>null</code> if not set.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the position.
   * @return a position or <code>null</code> if not set.
   */
  public ExtendedPosition getPosition()
  {
    return _position;
  }

  /**
   * Set the position.
   * @param position the position to set.
   */
  public void setPosition(ExtendedPosition position)
  {
    _position=position;
  }

  @Override
  public String toString()
  {
    return "AnchorStatus: type="+_type+", name="+_name+", position="+_position;
  }
}
