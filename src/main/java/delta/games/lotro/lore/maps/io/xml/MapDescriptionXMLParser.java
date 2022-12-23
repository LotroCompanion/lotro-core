package delta.games.lotro.lore.maps.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;

import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.lore.geo.GeoBoundingBox;
import delta.games.lotro.lore.maps.MapDescription;

/**
 * Parser for the map descriptions stored in XML.
 * @author DAM
 */
public class MapDescriptionXMLParser
{
  /**
   * Build a map description from an XML tag (for private encounters only!).
   * @param rootTag Root tag.
   * @return A parchment maps manager.
   */
  public static MapDescription parseMapDescription(Element rootTag)
  {
    MapDescription ret=new MapDescription();
    parseMapDescription(rootTag,ret);
    return ret;
  }

  private static void parseMapDescription(Element mapDescriptionTag, MapDescription map)
  {
    NamedNodeMap attrs=mapDescriptionTag.getAttributes();
    // ID
    int mapIdInt=DOMParsingTools.getIntAttribute(attrs,MapDescriptionXMLConstants.MAP_ID_ATTR,0);
    Integer mapId=(mapIdInt!=0)?Integer.valueOf(mapIdInt):null;
    map.setMapId(mapId);
    // Region
    int region=DOMParsingTools.getIntAttribute(attrs,MapDescriptionXMLConstants.REGION_ATTR,0);
    map.setRegion(region);
    // Bounding box
    GeoBoundingBox boundingBox=parseBoundingBox(mapDescriptionTag);
    map.setBoundingBox(boundingBox);
  }

  private static GeoBoundingBox parseBoundingBox(Element boundinBoxTag)
  {
    GeoBoundingBox ret=null;
    NamedNodeMap attrs=boundinBoxTag.getAttributes();
    Node tmp=attrs.getNamedItem(MapDescriptionXMLConstants.MIN_LON_ATTR);
    if (tmp!=null)
    {
      // Min
      float lonMin=DOMParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MIN_LON_ATTR,0);
      float latMin=DOMParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MIN_LAT_ATTR,0);
      // Max
      float lonMax=DOMParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MAX_LON_ATTR,0);
      float latMax=DOMParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MAX_LAT_ATTR,0);
      ret=new GeoBoundingBox(lonMin,latMin,lonMax,latMax);
    }
    return ret;
  }

  /**
   * Build a map description from an XML tag.
   * @param attrs Input values.
   * @return A parchment maps manager.
   */
  public static MapDescription parseMapDescription(Attributes attrs)
  {
    MapDescription ret=new MapDescription();
    parseMapDescription(attrs,ret);
    return ret;
  }

  private static void parseMapDescription(Attributes attrs, MapDescription map)
  {
    // ID
    int mapIdInt=SAXParsingTools.getIntAttribute(attrs,MapDescriptionXMLConstants.MAP_ID_ATTR,0);
    Integer mapId=(mapIdInt!=0)?Integer.valueOf(mapIdInt):null;
    map.setMapId(mapId);
    // Region
    int region=SAXParsingTools.getIntAttribute(attrs,MapDescriptionXMLConstants.REGION_ATTR,0);
    map.setRegion(region);
    // Bounding box
    GeoBoundingBox boundingBox=parseBoundingBox(attrs);
    map.setBoundingBox(boundingBox);
  }

  private static GeoBoundingBox parseBoundingBox(Attributes attrs)
  {
    GeoBoundingBox ret=null;
    String tmp=attrs.getValue(MapDescriptionXMLConstants.MIN_LON_ATTR);
    if (tmp!=null)
    {
      // Min
      float lonMin=SAXParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MIN_LON_ATTR,0);
      float latMin=SAXParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MIN_LAT_ATTR,0);
      // Max
      float lonMax=SAXParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MAX_LON_ATTR,0);
      float latMax=SAXParsingTools.getFloatAttribute(attrs,MapDescriptionXMLConstants.MAX_LAT_ATTR,0);
      ret=new GeoBoundingBox(lonMin,latMin,lonMax,latMax);
    }
    return ret;
  }
}
