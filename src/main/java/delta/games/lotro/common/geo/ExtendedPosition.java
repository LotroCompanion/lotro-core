package delta.games.lotro.common.geo;

import delta.games.lotro.lore.maps.Zone;
import delta.games.lotro.lore.maps.ZoneUtils;
import delta.games.lotro.utils.Proxy;

/**
 * Position, extended with geographic zone (area or dungeon).
 * @author DAM
 */
public class ExtendedPosition
{
  private Position _position;
  private Proxy<Zone> _zone;

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
    if (_zone!=null)
    {
      Zone zone=_zone.getObject();
      if (zone==null)
      {
        zone=ZoneUtils.getZone(_zone.getId());
        setZone(zone);
      }
      return zone;
    }
    return null;
  }

  /**
   * Get the zone identifier.
   * @return A zone identifier or <code>null</code> if none.
   */
  public Integer getZoneID()
  {
    return (_zone!=null)?Integer.valueOf(_zone.getId()):null;
  }

  /**
   * Set the zone ID.
   * @param zoneID Zone ID.
   */
  public void setZoneID(Integer zoneID)
  {
    if (zoneID!=null)
    {
      _zone=new Proxy<Zone>();
      _zone.setId(zoneID.intValue());
    }
    else
    {
      _zone=null;
    }
  }

  /**
   * Set the zone.
   * @param zone the zone to set.
   */
  public void setZone(Zone zone)
  {
    if (zone!=null)
    {
      Proxy<Zone> proxy=new Proxy<Zone>();
      proxy.setId(zone.getIdentifier());
      proxy.setName(zone.getName());
      proxy.setObject(zone);
      _zone=proxy;
    }
    else
    {
      _zone=null;
    }
  }

  @Override
  public String toString()
  {
    return ((_position!=null)?_position.toString():"?")+" - "+((_zone!=null)?_zone.getName():"?");
  }
}
