package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLParser;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedType;
import delta.games.lotro.lore.deeds.geo.DeedGeoData;
import delta.games.lotro.lore.deeds.geo.DeedGeoPoint;
import delta.games.lotro.lore.quests.io.xml.AchievableXMLParser;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLParser;

/**
 * Parser for deed descriptions stored in XML.
 * @author DAM
 */
public class DeedXMLParser extends AchievableXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed deed or <code>null</code>.
   */
  public List<DeedDescription> parseXML(File source)
  {
    List<DeedDescription> ret=new ArrayList<DeedDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      String tagName=root.getTagName();
      if (DeedXMLConstants.DEED_TAG.equals(tagName))
      {
        DeedDescription deed=parseDeed(root);
        ret.add(deed);
      }
      else
      {
        List<Element> deedTags=DOMParsingTools.getChildTagsByName(root,DeedXMLConstants.DEED_TAG);
        for(Element deedTag : deedTags)
        {
          DeedDescription deed=parseDeed(deedTag);
          ret.add(deed);
        }
      }
    }
    return ret;
  }

  /**
   * Parse the XML stream.
   * @param source Source stream.
   * @return Parsed deed or <code>null</code>.
   */
  public DeedDescription parseXML(InputStream source)
  {
    DeedDescription deed=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      deed=parseDeed(root);
    }
    return deed;
  }

  private DeedDescription parseDeed(Element root)
  {
    DeedDescription deed=new DeedDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Shared attributes
    parseAchievableAttributes(attrs,deed);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_KEY_ATTR,null);
    deed.setKey(key);
    // Type
    DeedType type=null;
    String typeStr=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_TYPE_ATTR,null);
    if (typeStr!=null)
    {
      try
      {
        type=DeedType.valueOf(typeStr);
      }
      catch(Exception e)
      {
        // Ignored
      }
    }
    deed.setType(type);
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(deed.getUsageRequirement(),root);
    // Prerequisites
    parsePrerequisites(root,deed);
    // Objectives
    ObjectivesXMLParser.loadObjectives(root,deed.getObjectives());
    // Geographic data
    DeedGeoData data=parseGeoData(root);
    deed.setGeoData(data);
    // Rewards
    RewardsXMLParser.loadRewards(root,deed.getRewards());
    return deed;
  }

  private DeedGeoData parseGeoData(Element root)
  {
    Element geoTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.GEO_TAG);
    if (geoTag==null)
    {
      return null;
    }
    // Geo deed data
    NamedNodeMap attrs=root.getAttributes();
    int nbPoints=DOMParsingTools.getIntAttribute(attrs,DeedXMLConstants.GEO_REQUIRED_POINTS_ATTR,0);
    DeedGeoData data=new DeedGeoData(nbPoints);
    // Geo points
    List<Element> geoPointTags=DOMParsingTools.getChildTagsByName(root,DeedXMLConstants.POINT_TAG);
    for(Element geoPointTag : geoPointTags)
    {
      NamedNodeMap geoPointAttr=geoPointTag.getAttributes();
      int pointId=DOMParsingTools.getIntAttribute(geoPointAttr,DeedXMLConstants.POINT_ID_ATTR,0);
      int mapId=DOMParsingTools.getIntAttribute(geoPointAttr,DeedXMLConstants.POINT_MAP_KEY_ATTR,0);
      if ((mapId!=0) && (pointId!=0))
      {
        DeedGeoPoint point=new DeedGeoPoint(mapId,pointId);
        data.addPoint(point);
      }
    }
    return data;
  }
}
