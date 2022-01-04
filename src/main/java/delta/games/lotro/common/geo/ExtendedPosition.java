package delta.games.lotro.common.geo;

import delta.games.lotro.lore.maps.Zone;

/**
 * Position, extended with geographic zone (area or dungeon).
 * @author DAM
 */
public class ExtendedPosition
{
  private Position _position;
  private Zone _zone;

  /**
   * Constructor.
   */
  public ExtendedPosition()
  {
    _position=null;
    _zone=null;
  }

  /**
   * Get the raw position.
   * @return a position or <code>null</code> if not set.
   */
  public Position getPosition()
  {
    return _position;
  }

  /**
   * Set the position.
   * @param position the position to set.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }

  /**
   * Get the associated zone.
   * @return a zone or <code>null</code> if not set.
   */
  public Zone getZone()
  {
    return _zone;
  }

  /**
   * Set the zone.
   * @param zone the zone to set.
   */
  public void setZone(Zone zone)
  {
    _zone=zone;
  }

  @Override
  public String toString()
  {
    return ((_position!=null)?_position.toString():"?")+" - "+((_zone!=null)?_zone.getName():"?");
  }
}
