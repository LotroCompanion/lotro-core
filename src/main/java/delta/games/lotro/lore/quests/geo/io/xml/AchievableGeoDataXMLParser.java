package delta.games.lotro.lore.quests.geo.io.xml;

import java.awt.geom.Point2D;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Reads geo data for objective conditions.
 * @author DAM
 */
public class AchievableGeoDataXMLParser
{
  /**
   * Read geo data for a single objective condition.
   * @param conditionTag Condition tag.
   * @param condition Condition to use.
   */
  public static void parseGeoData(Element conditionTag, ObjectiveCondition condition)
  {
    List<Element> pointTags=DOMParsingTools.getChildTagsByName(conditionTag,AchievableGeoDataXMLConstants.POINT_TAG);
    for(Element pointTag : pointTags)
    {
      NamedNodeMap pointAttrs=pointTag.getAttributes();
      // DID
      int did=DOMParsingTools.getIntAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_DID_ATTR,0);
      // Key
      String key=DOMParsingTools.getStringAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_KEY_ATTR,null);
      // Map ID
      int mapId=DOMParsingTools.getIntAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_MAP_ID_ATTR,0);
      // Old marker ID
      int oldMarkerId=DOMParsingTools.getIntAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_OLD_MARKER_ID_ATTR,0);
      // Position
      // - longitude
      float longitude=DOMParsingTools.getFloatAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_LONGITUDE_ATTR,0);
      // - latitude
      float latitude=DOMParsingTools.getFloatAttribute(pointAttrs,AchievableGeoDataXMLConstants.POINT_LATITUDE_ATTR,0);
      Point2D.Float lonLat=new Point2D.Float(longitude,latitude);
      AchievableGeoPoint point=new AchievableGeoPoint(did,key,mapId,lonLat);
      if (oldMarkerId!=0)
      {
        point.setOldMarkerId(Integer.valueOf(oldMarkerId));
      }
      condition.addPoint(point);
    }
  }
}
