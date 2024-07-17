package delta.games.lotro.lore.maps;

import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.maps.landblocks.Landblock;
import delta.games.lotro.lore.maps.landblocks.LandblocksManager;

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

  /**
   * Get the area for the given block.
   * @param block Block to use.
   * @return An area ID or <code>null</code>.
   */
  public static Integer getAreaForBlock(BlockReference block)
  {
    LandblocksManager mgr=LandblocksManager.getInstance();
    Landblock lb=mgr.getLandblock(block.getRegion(),block.getBlockX(),block.getBlockY());
    if (lb!=null)
    {
      return lb.getParentArea();
    }
    return null;
  }
}
