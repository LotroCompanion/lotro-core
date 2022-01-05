package delta.games.lotro.lore.maps;

/**
 * Utility methods related to zones.
 * @author DAM
 */
public class ZoneUtils
{
  /**
   * Get a zone using its identifier.
   * @param zoneId Zone identifier.
   * @return A zone or <code>null</code> if not found.
   */
  public static Zone getZone(int zoneId)
  {
    // Dungeon?
    DungeonsManager dungeonsManager=DungeonsManager.getInstance();
    Dungeon dungeon=dungeonsManager.getDungeonById(zoneId);
    if (dungeon!=null)
    {
      return dungeon;
    }
    // Area?
    GeoAreasManager geoAreasManager=GeoAreasManager.getInstance();
    Area area=geoAreasManager.getAreaById(zoneId);
    return area;
  }
}
