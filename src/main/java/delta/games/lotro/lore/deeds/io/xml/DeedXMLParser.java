package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLParser;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedProxies;
import delta.games.lotro.lore.deeds.DeedProxy;
import delta.games.lotro.lore.deeds.DeedType;
import delta.games.lotro.lore.deeds.geo.DeedGeoData;
import delta.games.lotro.lore.deeds.geo.DeedGeoPoint;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLParser;

/**
 * Parser for deed descriptions stored in XML.
 * @author DAM
 */
public class DeedXMLParser
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

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,DeedXMLConstants.DEED_ID_ATTR,0);
    deed.setIdentifier(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_KEY_ATTR,null);
    deed.setKey(key);
    // Name
    String title=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_NAME_ATTR,null);
    deed.setName(title);
    // Type
    DeedType type=null;
    String typeStr=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_TYPE_ATTR,null);
    if (typeStr!=null)
    {
      type=DeedType.valueOf(typeStr);
    }
    deed.setType(type);
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(deed.getUsageRequirement(),root);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_CATEGORY_ATTR,null);
    deed.setCategory(category);
    // Challenge level
    byte challengeLevel=(byte)DOMParsingTools.getIntAttribute(attrs,DeedXMLConstants.DEED_CHALLENGE_LEVEL_ATTR,0);
    deed.setChallengeLevel(ChallengeLevel.getByCode(challengeLevel));
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_DESCRIPTION_ATTR,"");
    deed.setDescription(description);
    // Objectives string
    String objectives=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_OBJECTIVES_ATTR,null);
    deed.setObjectivesString(objectives);
    // Objectives
    ObjectivesXMLParser.loadObjectives(root,deed.getObjectives());

    // Previous deed
    Element previousTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.PREVIOUS_TAG);
    DeedProxy previous=parseDeedProxy(previousTag);
    deed.setPreviousDeedProxy(previous);
    // Next deed
    Element nextTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.NEXT_TAG);
    DeedProxy next=parseDeedProxy(nextTag);
    deed.setNextDeedProxy(next);
    // Parent deeds
    List<Element> parentTags=DOMParsingTools.getChildTagsByName(root,DeedXMLConstants.PARENT_TAG);
    DeedProxies parents=deed.getParentDeedProxies();
    for(Element parentDeedTag : parentTags)
    {
      DeedProxy parentDeedProxy=parseDeedProxy(parentDeedTag);
      parents.add(parentDeedProxy);
    }
    // Child deeds
    List<Element> childDeedTags=DOMParsingTools.getChildTagsByName(root,DeedXMLConstants.CHILD_TAG);
    DeedProxies children=deed.getChildDeedProxies();
    for(Element childDeedTag : childDeedTags)
    {
      DeedProxy childDeedProxy=parseDeedProxy(childDeedTag);
      children.add(childDeedProxy);
    }
    // Geographic data
    DeedGeoData data=parseGeoData(root);
    deed.setGeoData(data);
    // Rewards
    RewardsXMLParser.loadRewards(root,deed.getRewards());
    return deed;
  }

  private DeedProxy parseDeedProxy(Element root)
  {
    if (root==null)
    {
      return null;
    }
    DeedProxy proxy=new DeedProxy();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,DeedXMLConstants.DEED_PROXY_ID_ATTR,0);
    proxy.setId(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_PROXY_KEY_ATTR,null);
    proxy.setKey(key);
    // Name
    String title=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_PROXY_NAME_ATTR,null);
    proxy.setName(title);
    return proxy;
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
      String mapKey=DOMParsingTools.getStringAttribute(geoPointAttr,DeedXMLConstants.POINT_MAP_KEY_ATTR,null);
      if ((mapKey!=null) && (pointId!=0))
      {
        DeedGeoPoint point=new DeedGeoPoint(mapKey,pointId);
        data.addPoint(point);
      }
    }
    return data;
  }
}
