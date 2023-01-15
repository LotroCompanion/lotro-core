package delta.games.lotro.common.geo.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.geo.Position;

/**
 * Reads position data from an XML document.
 * @author DAM
 */
public class PositionXMLParser
{
  /**
   * Read a position from an XML tag.
   * @param positionTag Position tag.
   * @return A position or <code>null</code>.
   */
  public static ExtendedPosition parsePosition(Element positionTag)
  {
    if (positionTag==null)
    {
      return null;
    }
    ExtendedPosition extendedPosition=new ExtendedPosition();
    NamedNodeMap positionAttrs=positionTag.getAttributes();
    // Raw position
    Position position=parseSimplePosition(positionTag);
    extendedPosition.setPosition(position);
    // Zone
    int zoneID=DOMParsingTools.getIntAttribute(positionAttrs,PositionXMLConstants.POSITION_ZONE_ID_ATTR,0);
    extendedPosition.setZoneID((zoneID!=0)?Integer.valueOf(zoneID):null);
    return extendedPosition;
  }

  /**
   * Read a position from an XML tag.
   * @param positionTag Position tag.
   * @return A position or <code>null</code>.
   */
  public static Position parseSimplePosition(Element positionTag)
  {
    if (positionTag==null)
    {
      return null;
    }
    NamedNodeMap positionAttrs=positionTag.getAttributes();
    int region=DOMParsingTools.getIntAttribute(positionAttrs,PositionXMLConstants.POSITION_REGION_ATTR,0);
    float longitude=DOMParsingTools.getFloatAttribute(positionAttrs,PositionXMLConstants.POSITION_LONGITUDE_ATTR,0);
    float latitude=DOMParsingTools.getFloatAttribute(positionAttrs,PositionXMLConstants.POSITION_LATITUDE_ATTR,0);
    Position position=new Position(region,longitude,latitude);
    return position;
  }
}
