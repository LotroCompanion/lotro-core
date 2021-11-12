package delta.games.lotro.lore.allegiances.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;

/**
 * Parser for allegiances stored in XML.
 * @author DAM
 */
public class AllegianceXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed allegiances.
   */
  public List<AllegianceDescription> parseXML(File source)
  {
    List<AllegianceDescription> ret=new ArrayList<AllegianceDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> allegianceTags=DOMParsingTools.getChildTagsByName(root,AllegianceXMLConstants.ALLEGIANCE_TAG);
      for(Element allegianceTag : allegianceTags)
      {
        AllegianceDescription allegiance=parseAllegiance(allegianceTag);
        ret.add(allegiance);
      }
    }
    return ret;
  }

  private AllegianceDescription parseAllegiance(Element root)
  {
    AllegianceDescription allegiance=new AllegianceDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_ID_ATTR,0);
    allegiance.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_NAME_ATTR,"");
    allegiance.setName(name);
    // Icon
    int iconId=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_ICON_ATTR,0);
    allegiance.setIconId(iconId);
    // Group
    String group=DOMParsingTools.getStringAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_GROUP_ATTR,"");
    allegiance.setGroup(group);
    // Min Level
    int minLevel=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_MIN_LEVEL_ATTR,-1);
    allegiance.setMinLevel(minLevel>=0?Integer.valueOf(minLevel):null);
    // Travel skill
    int travelSkillID=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_TRAVEL_SKILL_ID_ATTR,0);
    if (travelSkillID>0)
    {
      SkillDescription skill=SkillsManager.getInstance().getSkill(travelSkillID);
      allegiance.setTravelSkill(skill);
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_DESCRIPTION_ATTR,"");
    allegiance.setDescription(description);
    // Deeds
    List<Element> deedTags=DOMParsingTools.getChildTagsByName(root,AllegianceXMLConstants.DEED_TAG);
    for(Element deedTag : deedTags)
    {
      NamedNodeMap deedAttrs=deedTag.getAttributes();
      int deedId=DOMParsingTools.getIntAttribute(deedAttrs,AllegianceXMLConstants.DEED_ID_ATTR,0);
      DeedDescription deed=DeedsManager.getInstance().getDeed(deedId);
      allegiance.addDeed(deed);
    }
    return allegiance;
  }
}
