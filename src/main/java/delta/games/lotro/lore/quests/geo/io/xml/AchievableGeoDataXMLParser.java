package delta.games.lotro.lore.quests.geo.io.xml;

import java.awt.geom.Point2D;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;

/**
 * Reads geo data for objective conditions.
 * @author DAM
 */
public class AchievableGeoDataXMLParser
{
  /**
   * Parse an achievable geo point (SAX mode).
   * @param pointAttrs Input data.
   * @return the loaded point.
   */
  public static AchievableGeoPoint parseGeoDataElement(Attributes pointAttrs)
  {
    // DID
    int did=SAXParsingTools.getIntAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_DID_ATTR,0);
    // Key
    String key=SAXParsingTools.getStringAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_KEY_ATTR,null);
    // Map index
    int mapIndex=SAXParsingTools.getIntAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_MAP_INDEX_ATTR,0);
    // Position
    // - longitude
    float longitude=SAXParsingTools.getFloatAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_LONGITUDE_ATTR,0);
    // - latitude
    float latitude=SAXParsingTools.getFloatAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_LATITUDE_ATTR,0);
    Point2D.Float lonLat=new Point2D.Float(longitude,latitude);
    AchievableGeoPoint point=new AchievableGeoPoint(did,key,mapIndex,lonLat);
    return point;
  }
}
