package delta.games.lotro.lore.crafting.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.CraftingLevelTier;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Professions;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.crafting.Vocations;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Parser for crafting data stored in XML.
 * @author DAM
 */
public class CraftingXMLParser
{
  /**
   * Parse crafting data from an XML file.
   * @param source Source file.
   * @return the parsed data.
   */
  public static CraftingData parseCraftingSystem(File source)
  {
    CraftingData ret=new CraftingData();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // Professions
      Professions professions=ret.getProfessionsRegistry();
      List<Element> professionTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.PROFESSION_TAG,false);
      for(Element professionTag : professionTags)
      {
        Profession profession=parseProfession(professionTag);
        professions.addProfession(profession);
      }
      // Vocations
      Vocations vocations=ret.getVocationsRegistry();
      List<Element> vocationTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.VOCATION_TAG,false);
      for(Element vocationTag : vocationTags)
      {
        Vocation vocation=parseVocation(vocationTag,ret);
        vocations.addVocation(vocation);
      }
    }
    return ret;
  }

  /**
   * Build a profession from an XML tag.
   * @param root Root XML tag.
   * @return A profession.
   */
  private static Profession parseProfession(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    Profession ret=new Profession();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.PROFESSION_IDENTIFIER_ATTR,0);
    ret.setIdentifier(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_KEY_ATTR,null);
    ret.setKey(key);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_NAME_ATTR,"");
    ret.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_DESCRIPTION_ATTR,"");
    ret.setDescription(description);

    List<Element> tierTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.PROFESSION_TIER_TAG);
    for(Element tierTag : tierTags)
    {
      CraftingLevel level=parseCraftingLevel(tierTag);
      ret.addLevel(level);
    }
    return ret;
  }

  private static CraftingLevel parseCraftingLevel(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.PROFESSION_TIER_ATTR,0);
    CraftingLevel ret=new CraftingLevel(tier);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_TIER_NAME_ATTR,"");
    ret.setName(name);
    // Icon
    String icon=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_TIER_ICON_ATTR,"");
    ret.setIcon(icon);
    // Proficiency
    Element proficiencyTag=DOMParsingTools.getChildTagByName(root,CraftingXMLConstants.PROFICIENCY_TAG);
    parseCraftingLevelStep(proficiencyTag,ret.getProficiency());
    // Mastery
    Element masteryTag=DOMParsingTools.getChildTagByName(root,CraftingXMLConstants.MASTERY_TAG);
    parseCraftingLevelStep(masteryTag,ret.getMastery());
    return ret;
  }

  private static void parseCraftingLevelStep(Element root, CraftingLevelTier tier)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Title ID
    int titleId=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.STEP_TITLE_ID_ATTR,0);
    if (titleId!=0)
    {
      TitleDescription title=TitlesManager.getInstance().getTitle(titleId);
      tier.setTitle(title);
    }
    // XP
    int xp=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.STEP_XP_NAME_ATTR,0);
    tier.setXP(xp);
  }

  /**
   * Build a vocation from an XML tag.
   * @param root Root XML tag.
   * @param crafting Crafting system.
   * @return A vocation.
   */
  private static Vocation parseVocation(Element root, CraftingData crafting)
  {
    NamedNodeMap attrs=root.getAttributes();
    Vocation ret=new Vocation();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.VOCATION_IDENTIFIER_ATTR,0);
    ret.setIdentifier(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.VOCATION_KEY_ATTR,null);
    ret.setKey(key);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.VOCATION_NAME_ATTR,"");
    ret.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.VOCATION_DESCRIPTION_ATTR,"");
    ret.setDescription(description);

    Professions professions=crafting.getProfessionsRegistry();
    List<Element> professionTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.PROFESSION_TAG);
    for(Element professionTag : professionTags)
    {
      NamedNodeMap professionAttrs=professionTag.getAttributes();
      // Profession identifier
      int professionId=DOMParsingTools.getIntAttribute(professionAttrs,CraftingXMLConstants.PROFESSION_IDENTIFIER_ATTR,0);
      Profession profession=professions.getProfessionById(professionId);
      ret.addProfession(profession);
    }
    return ret;
  }

}
