package delta.games.lotro.lore.instances;

/**
 * Zone and map data.
 * @author DAM
 */
public class ZoneAndMap
{
  private int _zoneId;
  private Integer _mapId;

  /**
   * Constructor.
   * @param zoneId Zone identifier (dungeon or area).
   * @param mapId Basemap identifier.
   */
  public ZoneAndMap(int zoneId, Integer mapId)
  {
    _zoneId=zoneId;
    _mapId=mapId;
  }

  /**
   * Get the zone identifier.
   * @return a zone identifier.
   */
  public int getZoneId()
  {
    return _zoneId;
  }

  /**
   * Get the basemap identifier.
   * @return a basemap identifier (may be <code>null</code>).
   */
  public Integer getMapId()
  {
    return _mapId;
  }

  @Override
  public String toString()
  {
    return "Zone="+_zoneId+", map="+_mapId;
  }
}
