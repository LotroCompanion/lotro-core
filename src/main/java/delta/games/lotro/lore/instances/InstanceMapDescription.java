package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.maps.MapDescription;

/**
 * Description of an instance map.
 * @author DAM
 */
public class InstanceMapDescription
{
  private MapDescription _map;
  private List<Integer> _zoneIds;

  /**
   * Constructor.
   */
  public InstanceMapDescription()
  {
    super();
    _map=null;
    _zoneIds=new ArrayList<Integer>();
  }

  /**
   * Get the associated basemap.
   * @return a basemap or <code>null</code> if not set (should be...).
   */
  public MapDescription getMap()
  {
    return _map;
  }

  /**
   * Set the associated map.
   * @param map Map to set.
   */
  public void setMap(MapDescription map)
  {
    _map=map;
  }

  /**
   * Add a zone ID for this map.
   * @param zoneId Zone identifier (dungeon or area).
   */
  public void addZoneId(int zoneId)
  {
    _zoneIds.add(Integer.valueOf(zoneId));
  }

  /**
   * Remove a zone ID for this map.
   * @param zoneId Zone identifier (dungeon or area).
   */
  public void removeZone(int zoneId)
  {
    _zoneIds.remove(Integer.valueOf(zoneId));
  }

  /**
   * Get all zone identifiers for this map.
   * @return a list of zone identifiers. 
   */
  public List<Integer> getZoneIds()
  {
    return _zoneIds;
  }

  @Override
  public String toString()
  {
    return "Instance map: "+_map+", zones="+_zoneIds;
  }
}
