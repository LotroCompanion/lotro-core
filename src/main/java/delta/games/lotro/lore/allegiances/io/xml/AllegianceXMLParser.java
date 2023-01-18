package delta.games.lotro.lore.allegiances.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.common.enums.AllegianceGroup;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.AllegiancesManager;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;
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
  public AllegiancesManager parseXML(File source)
  {
    AllegiancesManager ret=new AllegiancesManager();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // Allegiances
      List<Element> allegianceTags=DOMParsingTools.getChildTagsByName(root,AllegianceXMLConstants.ALLEGIANCE_TAG);
      for(Element allegianceTag : allegianceTags)
      {
        AllegianceDescription allegiance=parseAllegiance(allegianceTag);
        ret.addAllegiance(allegiance);
      }
      // Curves
      List<Element> curveTags=DOMParsingTools.getChildTagsByName(root,AllegianceXMLConstants.CURVE_TAG);
      for(Element curveTag : curveTags)
      {
        Points2LevelCurve curve=parseCurve(curveTag);
        ret.getCurvesManager().addCurve(curve);
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
    int groupCode=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ALLEGIANCE_GROUP_ATTR,0);
    LotroEnum<AllegianceGroup> groupEnum=LotroEnumsRegistry.getInstance().get(AllegianceGroup.class);
    AllegianceGroup group=groupEnum.getEntry(groupCode);
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

  private Points2LevelCurve parseCurve(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,AllegianceXMLConstants.ID_ATTR,0);

    // Points
    List<Element> levelTags=DOMParsingTools.getChildTagsByName(root,AllegianceXMLConstants.LEVEL_TAG);
    int[] values=new int[levelTags.size()];
    for(Element levelTag : levelTags)
    {
      NamedNodeMap levelAttrs=levelTag.getAttributes();
      int level=DOMParsingTools.getIntAttribute(levelAttrs,AllegianceXMLConstants.LEVEL_ATTR,0);
      int points=DOMParsingTools.getIntAttribute(levelAttrs,AllegianceXMLConstants.POINTS_ATTR,0);
      values[level]=points;
    }
    Points2LevelCurve ret=new Points2LevelCurve(id,values);
    return ret;
  }
}
