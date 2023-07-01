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
    int regionCode=position.getRegion();
    Region region=GeoAreasManager.getInstance().getRegionByCode(regionCode);
    return getLabel(latitude,longitude,region);
  }

  /**
   * Get a displayable label for a position.
   * @param latitude Latitude.
   * @param longitude Longitude.
   * @param region Region to use (may be <code>null</code>).
   * @return A label.
   */
  public static String getLabel(float latitude, float longitude, Region region)
  {
    boolean isSouth=(latitude<0);
    boolean isWest=(longitude<0);
    String latStr=L10n.getString(Math.abs(latitude),1);
    String lonStr=L10n.getString(Math.abs(longitude),1);
    String positionStr=latStr+(isSouth?'S':'N')+", "+lonStr+(isWest?'W':'E');
    if (region!=null)
    {
      positionStr=positionStr+" ("+region.getName()+")";
    }
    return positionStr;
  }
}
