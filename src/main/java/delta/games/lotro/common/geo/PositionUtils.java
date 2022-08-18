package delta.games.lotro.common.geo;

import delta.common.utils.l10n.L10n;
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
    String latStr=L10n.getString(Math.abs(latitude),1);
    String lonStr=L10n.getString(Math.abs(longitude),1);
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
