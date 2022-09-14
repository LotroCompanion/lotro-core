package delta.games.lotro.lore.hobbies.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.hobbies.HobbyDescription;
import delta.games.lotro.lore.hobbies.HobbyTitleEntry;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Parser for hobby descriptions stored in XML.
 * @author DAM
 */
public class HobbyDescriptionXMLParser
{
  /**
   * Parse a hobbies XML file.
   * @param source Source file.
   * @return List of parsed hobbies.
   */
  public static List<HobbyDescription> parseHobbiesFile(File source)
  {
    List<HobbyDescription> hobbies=new ArrayList<HobbyDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> hobbyTags=DOMParsingTools.getChildTagsByName(root,HobbyDescriptionXMLConstants.HOBBY_TAG);
      for(Element hobbyTag : hobbyTags)
      {
        HobbyDescription hobby=parseHobby(hobbyTag);
        hobbies.add(hobby);
      }
    }
    return hobbies;
  }

  /**
   * Build a hobby from an XML tag.
   * @param root Root XML tag.
   * @return A hobby.
   */
  private static HobbyDescription parseHobby(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    HobbyDescription hobby=new HobbyDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_IDENTIFIER_ATTR,0);
    hobby.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_NAME_ATTR,null);
    hobby.setName(name);
    // Type
    int type=DOMParsingTools.getIntAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_TYPE_ATTR,0);
    hobby.setHobbyType(type);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_DESCRIPTION_ATTR,"");
    hobby.setDescription(description);
    // Trainer info
    String trainerInfo=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_TRAINER_INFO_ATTR,"");
    hobby.setTrainerDisplayInfo(trainerInfo);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_ICON_ID_ATTR,0);
    hobby.setIconId(iconId);
    // Daily proficiency gain limit
    int dailyProficiencyGainLimit=DOMParsingTools.getIntAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_DAILY_PROFICIENCY_GAIN_LIMIT_ATTR,0);
    hobby.setDailyProficiencyGainLimit(dailyProficiencyGainLimit);
    // Min level
    int minLevel=DOMParsingTools.getIntAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_MIN_LEVEL_ATTR,0);
    hobby.setMinLevel(minLevel);
    // Proficiency property name
    String proficiencyPropertyName=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_PROFICIENCY_PROPERTY_NAME_ATTR,"");
    hobby.setProficiencyPropertyName(proficiencyPropertyName);
    // Proficiency modifier property name
    String proficiencyModifierPropertyName=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_PROFICIENCY_MODIFIER_PROPERTY_NAME_ATTR,"");
    hobby.setProficiencyModifierPropertyName(proficiencyModifierPropertyName);
    // Proficiency modifier property name
    String treasureProfileOverridePropertyName=DOMParsingTools.getStringAttribute(attrs,HobbyDescriptionXMLConstants.HOBBY_TREASURE_PROFILE_OVERRIDE_PROPERTY_ATTR,"");
    hobby.setTreasureProfileOverridePropertyName(treasureProfileOverridePropertyName);

    // Items
    {
      List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,HobbyDescriptionXMLConstants.ITEM_TAG);
      for(Element itemTag : itemTags)
      {
        NamedNodeMap itemAttrs=itemTag.getAttributes();
        // Item identifier
        int itemID=DOMParsingTools.getIntAttribute(itemAttrs,HobbyDescriptionXMLConstants.ITEM_ID_ATTR,0);
        Item item=ItemsManager.getInstance().getItem(itemID);
        if (item!=null)
        {
          hobby.addItem(item);
        }
      }
    }
    // Titles
    {
      List<Element> titleTags=DOMParsingTools.getChildTagsByName(root,HobbyDescriptionXMLConstants.TITLE_TAG);
      for(Element titleTag : titleTags)
      {
        NamedNodeMap titleAttrs=titleTag.getAttributes();
        // Title identifier
        int titleID=DOMParsingTools.getIntAttribute(titleAttrs,HobbyDescriptionXMLConstants.TITLE_ID_ATTR,0);
        TitleDescription title=TitlesManager.getInstance().getTitle(titleID);
        // Proficiency
        int proficiency=DOMParsingTools.getIntAttribute(titleAttrs,HobbyDescriptionXMLConstants.TITLE_PROFICIENCY_ATTR,0);
        if (title!=null)
        {
          HobbyTitleEntry titleEntry=new HobbyTitleEntry(proficiency,title);
          hobby.addTitle(titleEntry);
        }
      }
    }
    // Rewards: TODO
    return hobby;
  }
}
