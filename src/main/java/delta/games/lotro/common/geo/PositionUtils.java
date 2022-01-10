package delta.games.lotro.common.geo;

import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.Region;

/**
 * Utility methods related to positions.
 * @author DAM
 */
public class PositionUtils
{
  /**
   * Get a displayable label for a position.
   * @param position Position to use.
   * @return A label.
   */
  public static String getLabel(Position position)
  {
    float latitude=position.getLatitude();
    float longitude=position.getLongitude();
    boolean isSouth=(latitude<0);
    boolean isWest=(longitude<0);
    String latStr=String.format("%.1f",Float.valueOf(Math.abs(latitude)));
    String lonStr=String.format("%.1f",Float.valueOf(Math.abs(longitude)));
    String positionStr=latStr+(isSouth?'S':'N')+", "+lonStr+(isWest?'W':'E');
    int regionCode=position.getRegion();
    Region region=GeoAreasManager.getInstance().getRegionByCode(regionCode);
    if (region!=null)
    {
      positionStr=positionStr+" ("+region.getName()+")";
    }
    return positionStr;
  }
}
