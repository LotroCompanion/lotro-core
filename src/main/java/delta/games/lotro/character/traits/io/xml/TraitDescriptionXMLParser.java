package delta.games.lotro.character.traits.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for trait descriptions stored in XML.
 * @author DAM
 */
public class TraitDescriptionXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(TraitDescriptionXMLParser.class);

  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public TraitDescriptionXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("traits");
  }

  /**
   * Parse a traits XML file.
   * @param source Source file.
   * @return List of parsed traits.
   */
  public List<TraitDescription> parseTraitsFile(File source)
  {
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,TraitDescriptionXMLConstants.TRAIT_TAG);
      for(Element traitTag:traitTags)
      {
        TraitDescription trait=parseTrait(traitTag);
        traits.add(trait);
      }
    }
    return traits;
  }

  /**
   * Build a trait from an XML tag.
   * @param root Root XML tag.
   * @return A trait.
   */
  private TraitDescription parseTrait(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    TraitDescription trait=new TraitDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_IDENTIFIER_ATTR,0);
    trait.setIdentifier(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_KEY_ATTR,"");
    trait.setKey(key);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    trait.setName(name);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_ICON_ID_ATTR,0);
    trait.setIconId(iconId);
    // Min level
    int minLevel=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_MIN_LEVEL_ATTR,1);
    trait.setMinLevel(minLevel);
    // Tiers
    int tiers=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_TIERS_ATTR,1);
    trait.setTiersCount(tiers);
    // Tier property
    String tierPropertyName=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_TIER_PROPERTY_ATTR,null);
    trait.setTierPropertyName(tierPropertyName);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    trait.setDescription(description);
    // Stats
    StatsProvider statsProvider=trait.getStatsProvider();
    StatsProviderXMLParser.parseStatsProvider(root,statsProvider);
    // Category
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_CATEGORY_ATTR,0);
    if (categoryCode!=0)
    {
      LotroEnum<SkillCategory> categoryMgr=registry.get(SkillCategory.class);
      SkillCategory category=categoryMgr.getEntry(categoryCode);
      trait.setCategory(category);
    }
    // Nature
    int natureCode=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_NATURE_ATTR,0);
    if (natureCode!=0)
    {
      LotroEnum<TraitNature> natureMgr=registry.get(TraitNature.class);
      TraitNature nature=natureMgr.getEntry(natureCode);
      trait.setNature(nature);
    }
    // Tooltip
    String tooltip=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_TOOLTIP_ATTR,"");
    tooltip=I18nRuntimeUtils.getLabel(_i18n,tooltip);
    trait.setTooltip(tooltip);
    // Cosmetic
    boolean cosmetic=DOMParsingTools.getBooleanAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_COSMETIC_ATTR,false);
    trait.setCosmetic(cosmetic);
    // Skills
    List<Element> skillTags=DOMParsingTools.getChildTagsByName(root,TraitDescriptionXMLConstants.TRAIT_SKILL_TAG);
    for(Element skillTag : skillTags)
    {
      NamedNodeMap skillAttrs=skillTag.getAttributes();
      int skillId=DOMParsingTools.getIntAttribute(skillAttrs,TraitDescriptionXMLConstants.SKILL_ID_ATTR,0);
      String skillName=DOMParsingTools.getStringAttribute(skillAttrs,TraitDescriptionXMLConstants.SKILL_NAME_ATTR,null);
      SkillsManager skillsMgr=SkillsManager.getInstance();
      SkillDescription skill=skillsMgr.getSkill(skillId);
      if (skill!=null)
      {
        trait.addSkill(skill);
      }
      else
      {
        LOGGER.warn("Skill not found: ID="+skillId+", name="+skillName);
      }
    }
    return trait;
  }
}
