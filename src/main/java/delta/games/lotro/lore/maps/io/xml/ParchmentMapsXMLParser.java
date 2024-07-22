package delta.games.lotro.lore.maps.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.maps.Area;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.ParchmentMap;
import delta.games.lotro.lore.maps.ParchmentMapsManager;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for the parchment maps stored in XML.
 * @author DAM
 */
public class ParchmentMapsXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public ParchmentMapsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("parchmentMaps");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public ParchmentMapsManager parseXML(File source)
  {
    ParchmentMapsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseParchmentMaps(root);
    }
    return ret;
  }

  /**
   * Build a parchment maps manager from an XML tag.
   * @param rootTag Root tag.
   * @return A parchment maps manager.
   */
  private ParchmentMapsManager parseParchmentMaps(Element rootTag)
  {
    ParchmentMapsManager mgr=new ParchmentMapsManager();

    // Maps
    List<Element> parchmentMapTags=DOMParsingTools.getChildTagsByName(rootTag,ParchmentMapsXMLConstants.PARCHMENT_MAP_TAG);
    for(Element parchmentMapTag : parchmentMapTags)
    {
      ParchmentMap parchmentMap=parseParchmentMap(parchmentMapTag);
      mgr.addParchmentMap(parchmentMap);
    }
    return mgr;
  }

  private ParchmentMap parseParchmentMap(Element parchmentMapTag)
  {
    NamedNodeMap attrs=parchmentMapTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,ParchmentMapsXMLConstants.ID_ATTR,0);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));

    ParchmentMap ret=new ParchmentMap(id,name);
    // Region
    int region=DOMParsingTools.getIntAttribute(attrs,ParchmentMapsXMLConstants.PARCHMENT_MAP_REGION_ATTR,0);
    ret.setRegion(region);
    // Parent map ID
    int parentMapId=DOMParsingTools.getIntAttribute(attrs,ParchmentMapsXMLConstants.PARCHMENT_PARENT_MAP_ID_ATTR,0);
    ret.setParentMapId(parentMapId);
    // Quest guide disabled
    boolean questGuideDisabled=DOMParsingTools.getBooleanAttribute(attrs,ParchmentMapsXMLConstants.PARCHMENT_QUEST_GUIDE_DISABLED_ATTR,false);
    ret.setQuestGuideDisabled(questGuideDisabled);
    // Areas
    List<Element> areaTags=DOMParsingTools.getChildTagsByName(parchmentMapTag,ParchmentMapsXMLConstants.AREA_TAG);
    for(Element areaTag : areaTags)
    {
      NamedNodeMap areaAttrs=areaTag.getAttributes();
      // ID
      int areaId=DOMParsingTools.getIntAttribute(areaAttrs,ParchmentMapsXMLConstants.ID_ATTR,0);
      // ... resolve:
      GeoAreasManager geoAreasMgr=GeoAreasManager.getInstance();
      Area area=geoAreasMgr.getAreaById(areaId);
      ret.addArea(area);
    }
    return ret;
  }
}
