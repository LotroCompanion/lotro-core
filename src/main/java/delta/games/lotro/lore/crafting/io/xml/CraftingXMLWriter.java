package delta.games.lotro.lore.crafting.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.CraftingLevelTier;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Writes crafting data to XML files.
 * @author DAM
 */
public class CraftingXMLWriter
{
  /**
   * Write crafting data to a XML file.
   * @param toFile File to write to.
   * @param crafting Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final CraftingData crafting)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCrafting(hd,crafting);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeCrafting(TransformerHandler hd, CraftingData crafting) throws SAXException
  {
    hd.startElement("","",CraftingXMLConstants.CRAFTING_TAG,new AttributesImpl());
    // Professions
    List<Profession> professions=crafting.getProfessionsRegistry().getAll();
    for(Profession profession : professions)
    {
      writeProfession(hd,profession);
    }
    // Vocation
    List<Vocation> vocations=crafting.getVocationsRegistry().getAll();
    for(Vocation vocation : vocations)
    {
      writeVocation(hd,vocation);
    }
    hd.endElement("","",CraftingXMLConstants.CRAFTING_TAG);
  }

  private static void writeVocation(TransformerHandler hd, Vocation vocation) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=vocation.getIdentifier();
    attrs.addAttribute("","",CraftingXMLConstants.VOCATION_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=vocation.getKey();
    if (key!=null)
    {
      attrs.addAttribute("","",CraftingXMLConstants.VOCATION_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=vocation.getName();
    attrs.addAttribute("","",CraftingXMLConstants.VOCATION_NAME_ATTR,XmlWriter.CDATA,name);
    // Description
    String description=vocation.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",CraftingXMLConstants.VOCATION_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(description));
    }
    hd.startElement("","",CraftingXMLConstants.VOCATION_TAG,attrs);
    // Professions
    for(Profession profession : vocation.getProfessions())
    {
      AttributesImpl professionAttrs=new AttributesImpl();
      // Identifier
      int professionId=profession.getIdentifier();
      professionAttrs.addAttribute("","",CraftingXMLConstants.PROFESSION_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(professionId));
      hd.startElement("","",CraftingXMLConstants.PROFESSION_TAG,professionAttrs);
      hd.endElement("","",CraftingXMLConstants.PROFESSION_TAG);
    }
    hd.endElement("","",CraftingXMLConstants.VOCATION_TAG);
  }

  private static void writeProfession(TransformerHandler hd, Profession profession) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=profession.getIdentifier();
    attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=profession.getKey();
    if (key!=null)
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=profession.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Description
    String description=profession.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(description));
    }
    // Guild?
    Faction guildFaction=profession.getGuildFaction();
    if (guildFaction!=null)
    {
      int guildFactionId=guildFaction.getIdentifier();
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_GUILD_FACTION_ATTR,XmlWriter.CDATA,String.valueOf(guildFactionId));
    }
    // Property names
    // - enabled?
    String enabledPropertyName=profession.getEnabledPropertyName();
    if ((enabledPropertyName!=null) && (enabledPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_ENABLED_PROPERTY_ATTR,XmlWriter.CDATA,enabledPropertyName);
    }
    // Mastery level
    String masteryLevelPropertyName=profession.getMasteryLevelPropertyName();
    if ((masteryLevelPropertyName!=null) && (masteryLevelPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_MASTERY_LEVEL_PROPERTY_ATTR,XmlWriter.CDATA,masteryLevelPropertyName);
    }
    // Mastery XP
    String masteryXpPropertyName=profession.getMasteryXpPropertyName();
    if ((masteryXpPropertyName!=null) && (masteryXpPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_MASTERY_XP_PROPERTY_ATTR,XmlWriter.CDATA,masteryXpPropertyName);
    }
    // Proficiency level
    String proficiencyLevelPropertyName=profession.getProficiencyLevelPropertyName();
    if ((proficiencyLevelPropertyName!=null) && (proficiencyLevelPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_PROFICIENCY_LEVEL_PROPERTY_ATTR,XmlWriter.CDATA,proficiencyLevelPropertyName);
    }
    // Proficiency XP
    String proficiencyXpPropertyName=profession.getProficiencyXpPropertyName();
    if ((proficiencyXpPropertyName!=null) && (proficiencyXpPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_PROFICIENCY_XP_PROPERTY_ATTR,XmlWriter.CDATA,proficiencyXpPropertyName);
    }
    // Extra recipes
    String extraRecipesPropertyName=profession.getExtraRecipesPropertyName();
    if ((extraRecipesPropertyName!=null) && (extraRecipesPropertyName.length()>0))
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_EXTRA_RECIPES_PROPERTY_ATTR,XmlWriter.CDATA,extraRecipesPropertyName);
    }
    hd.startElement("","",CraftingXMLConstants.PROFESSION_TAG,attrs);
    // Levels
    List<CraftingLevel> levels=profession.getLevels();
    for(CraftingLevel level : levels)
    {
      writeCraftingLevel(hd,level);
    }
    hd.endElement("","",CraftingXMLConstants.PROFESSION_TAG);
  }

  private static void writeCraftingLevel(TransformerHandler hd, CraftingLevel level) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Tier
    int tier=level.getTier();
    attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    // Name
    String tierName=level.getName();
    attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_TIER_NAME_ATTR,XmlWriter.CDATA,tierName);
    // Icon
    String icon=level.getIcon();
    if (icon!=null)
    {
      attrs.addAttribute("","",CraftingXMLConstants.PROFESSION_TIER_ICON_ATTR,XmlWriter.CDATA,icon);
    }
    hd.startElement("","",CraftingXMLConstants.PROFESSION_TIER_TAG,attrs);
    // Recipes
    int[] recipeIds=level.getRecipes();
    for(int recipeId : recipeIds)
    {
      AttributesImpl recipeAttrs=new AttributesImpl();
      recipeAttrs.addAttribute("","",CraftingXMLConstants.RECIPE_ID_ATTR,XmlWriter.CDATA,String.valueOf(recipeId));
      hd.startElement("","",CraftingXMLConstants.RECIPE_TAG,recipeAttrs);
      hd.endElement("","",CraftingXMLConstants.RECIPE_TAG);
    }
    // Proficiency
    writeCraftingLevelTier(hd,level.getProficiency(),CraftingXMLConstants.PROFICIENCY_TAG);
    // Mastery
    writeCraftingLevelTier(hd,level.getMastery(),CraftingXMLConstants.MASTERY_TAG);
    hd.endElement("","",CraftingXMLConstants.PROFESSION_TIER_TAG);
  }

  private static void writeCraftingLevelTier(TransformerHandler hd, CraftingLevelTier tier, String tagName) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Title
    TitleDescription title=tier.getTitle();
    if (title!=null)
    {
      int titleId=title.getIdentifier();
      attrs.addAttribute("","",CraftingXMLConstants.STEP_TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleId));
    }
    // XP
    int xp=tier.getXP();
    attrs.addAttribute("","",CraftingXMLConstants.STEP_XP_NAME_ATTR,XmlWriter.CDATA,String.valueOf(xp));
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }
}
