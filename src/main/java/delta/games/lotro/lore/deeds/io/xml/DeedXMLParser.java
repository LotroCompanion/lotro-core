package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.io.xml.RewardsXMLParser;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedProxy;
import delta.games.lotro.lore.deeds.DeedType;

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
    // Class
    String characterClass=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_CLASS_ATTR,"");
    CharacterClass cClass=CharacterClass.getByKey(characterClass);
    deed.setRequiredClass(cClass);
    // Race
    String race=DOMParsingTools.getStringAttribute(root.getAttributes(),DeedXMLConstants.DEED_RACE_ATTR,"");
    Race cRace=Race.getByKey(race); 
    deed.setRequiredRace(cRace);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_CATEGORY_ATTR,null);
    deed.setCategory(category);
    // Minimum level
    int minimumLevel=DOMParsingTools.getIntAttribute(attrs,DeedXMLConstants.DEED_MIN_LEVEL_ATTR,-1);
    if (minimumLevel!=-1)
    {
      deed.setMinLevel(Integer.valueOf(minimumLevel));
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_DESCRIPTION_ATTR,null);
    deed.setDescription(description);
    // Objectives
    String objectives=DOMParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_OBJECTIVES_ATTR,null);
    deed.setObjectives(objectives);

    // Previous deed
    Element previousTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.PREVIOUS_TAG);
    DeedProxy previous=parseDeedProxy(previousTag);
    deed.setPreviousDeedProxy(previous);
    // Next deed
    Element nextTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.NEXT_TAG);
    DeedProxy next=parseDeedProxy(nextTag);
    deed.setNextDeedProxy(next);
    // Parent deed
    Element parentTag=DOMParsingTools.getChildTagByName(root,DeedXMLConstants.PARENT_TAG);
    DeedProxy parent=parseDeedProxy(parentTag);
    deed.setParentDeedProxy(parent);
    // Child deeds
    List<Element> childDeedTags=DOMParsingTools.getChildTagsByName(root,DeedXMLConstants.CHILD_TAG);
    List<DeedProxy> childDeeds=deed.getChildDeeds();
    for(Element childDeedTag : childDeedTags)
    {
      DeedProxy childDeed=parseDeedProxy(childDeedTag);
      childDeeds.add(childDeed);
    }

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
}
