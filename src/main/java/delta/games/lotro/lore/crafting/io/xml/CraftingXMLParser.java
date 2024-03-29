package delta.games.lotro.lore.crafting.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.CraftTier;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.CraftingLevelTier;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Professions;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.crafting.Vocations;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for crafting data stored in XML.
 * @author DAM
 */
public class CraftingXMLParser
{
  private SingleLocaleLabelsManager _i18n;
  private LotroEnum<CraftTier> _craftTierEnum;

  /**
   * Constructor.
   */
  public CraftingXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("crafting");
    _craftTierEnum=LotroEnumsRegistry.getInstance().get(CraftTier.class);
  }

  /**
   * Parse crafting data from an XML file.
   * @param source Source file.
   * @return the parsed data.
   */
  public CraftingData parseCraftingSystem(File source)
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
  private Profession parseProfession(Element root)
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
    String name=_i18n.getLabel(String.valueOf(id));
    ret.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    ret.setDescription(description);
    // Guild?
    int guildFactionId=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.PROFESSION_GUILD_FACTION_ATTR,0);
    if (guildFactionId!=0)
    {
      FactionsRegistry factions=FactionsRegistry.getInstance();
      Faction guildFaction=factions.getById(guildFactionId);
      ret.setGuildFaction(guildFaction);
    }
    // Property names
    // - enabled
    String enabledPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_ENABLED_PROPERTY_ATTR,"");
    // - mastery level
    String masteryLevelPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_MASTERY_LEVEL_PROPERTY_ATTR,"");
    // - mastery XP
    String masteryXpPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_MASTERY_XP_PROPERTY_ATTR,"");
    // - proficiency level
    String proficiencyLevelPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_PROFICIENCY_LEVEL_PROPERTY_ATTR,"");
    // - proficiency XP
    String proficiencyXpPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_PROFICIENCY_XP_PROPERTY_ATTR,"");
    // - extra recipes
    String extraRecipesPropertyName=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_EXTRA_RECIPES_PROPERTY_ATTR,"");
    ret.setPropertyNames(enabledPropertyName,masteryLevelPropertyName,masteryXpPropertyName,proficiencyLevelPropertyName,proficiencyXpPropertyName,extraRecipesPropertyName);

    List<Element> tierTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.PROFESSION_TIER_TAG);
    for(Element tierTag : tierTags)
    {
      CraftingLevel level=parseCraftingLevel(ret,tierTag);
      ret.addLevel(level);
    }
    return ret;
  }

  private CraftingLevel parseCraftingLevel(Profession profession, Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Tier
    int tierCode=DOMParsingTools.getIntAttribute(attrs,CraftingXMLConstants.PROFESSION_TIER_ATTR,0);
    CraftTier tier=_craftTierEnum.getEntry(tierCode);
    CraftingLevel ret=new CraftingLevel(profession,tier);
    // Icon
    String icon=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.PROFESSION_TIER_ICON_ATTR,"");
    ret.setIcon(icon);
    // Recipes
    List<Element> recipeTags=DOMParsingTools.getChildTagsByName(root,CraftingXMLConstants.RECIPE_TAG);
    for(Element recipeTag : recipeTags)
    {
      int recipeId=DOMParsingTools.getIntAttribute(recipeTag.getAttributes(),CraftingXMLConstants.RECIPE_ID_ATTR,0);
      ret.addRecipe(recipeId);
    }
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
  private Vocation parseVocation(Element root, CraftingData crafting)
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
    String name=_i18n.getLabel(String.valueOf(id));
    ret.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CraftingXMLConstants.VOCATION_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
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
