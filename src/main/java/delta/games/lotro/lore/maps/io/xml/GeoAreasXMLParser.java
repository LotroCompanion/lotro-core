package delta.games.lotro.lore.maps.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.maps.Area;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.Region;
import delta.games.lotro.lore.maps.Territory;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for the geographic areas stored in XML.
 * @author DAM
 */
public class GeoAreasXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public GeoAreasXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("geoAreas");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public GeoAreasManager parseXML(File source)
  {
    GeoAreasManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseGeoAreas(root);
    }
    return ret;
  }

  /**
   * Build a geographic areas manager from an XML tag.
   * @param rootTag Root tag.
   * @return A geographic areas manager.
   */
  private GeoAreasManager parseGeoAreas(Element rootTag)
  {
    GeoAreasManager mgr=new GeoAreasManager();
    // Regions
    List<Element> regionTags=DOMParsingTools.getChildTagsByName(rootTag,GeoAreasXMLConstants.REGION_TAG);
    for(Element regionTag : regionTags)
    {
      Region region=parseRegion(regionTag);
      mgr.addRegion(region);
    }
    // Territories
    List<Element> territoryTags=DOMParsingTools.getChildTagsByName(rootTag,GeoAreasXMLConstants.TERRITORY_TAG);
    for(Element territoryTag : territoryTags)
    {
      Territory territory=parseTerritory(mgr,territoryTag);
      mgr.addTerritory(territory);
    }
    // Areas
    List<Element> areaTags=DOMParsingTools.getChildTagsByName(rootTag,GeoAreasXMLConstants.AREA_TAG);
    for(Element areaTag : areaTags)
    {
      Area area=parseArea(mgr,areaTag);
      mgr.addArea(area);
    }
    return mgr;
  }

  private Region parseRegion(Element regionTag)
  {
    NamedNodeMap attrs=regionTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.ID_ATTR,0);
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.REGION_CODE_ATTR,0);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    Region region=new Region(id,code,name);
    return region;
  }

  private Territory parseTerritory(GeoAreasManager mgr, Element territoryTag)
  {
    NamedNodeMap attrs=territoryTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.ID_ATTR,0);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    // Parent
    int regionId=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.PARENT_ID_ATTR,0);
    Region parentRegion=mgr.getRegionById(regionId);
    Territory territory=new Territory(id,name,parentRegion);
    return territory;
  }

  private Area parseArea(GeoAreasManager mgr, Element areaTag)
  {
    NamedNodeMap attrs=areaTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.ID_ATTR,0);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    // Parent
    int territoryId=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.PARENT_ID_ATTR,0);
    Territory parentTerritory=mgr.getTerritoryById(territoryId);
    Area area=new Area(id,name,parentTerritory);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,GeoAreasXMLConstants.AREA_ICON_ID_ATTR,0);
    if (iconId>0)
    {
      area.setIconId(Integer.valueOf(iconId));
    }
    return area;
  }
}
