package delta.games.lotro.character.traits.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.traits.EffectAtRank;
import delta.games.lotro.character.traits.SkillAtRank;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.prerequisites.AbstractTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.io.xml.TraitPrerequisitesXMLWriter;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.io.xml.EffectXMLWriter;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.TraitGroup;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.TraitSubCategory;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.utils.maths.ArrayProgression;

/**
 * Writes traits to XML files.
 * @author DAM
 */
public class TraitDescriptionXMLWriter
{
  private TraitPrerequisitesXMLWriter _prerequisitesWriter;

  /**
   * Constructor.
   */
  public TraitDescriptionXMLWriter()
  {
    _prerequisitesWriter=new TraitPrerequisitesXMLWriter();
  }

  /**
   * Write some traits to a XML file.
   * @param toFile File to write to.
   * @param traits Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<TraitDescription> traits)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        TraitDescriptionXMLWriter traitsWriter=new TraitDescriptionXMLWriter();
        hd.startElement("","",TraitDescriptionXMLConstants.TRAITS_TAG,new AttributesImpl());
        for(TraitDescription trait : traits)
        {
          traitsWriter.writeTrait(hd,trait);
        }
        hd.endElement("","",TraitDescriptionXMLConstants.TRAITS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeTrait(TransformerHandler hd, TraitDescription trait) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=trait.getIdentifier();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=trait.getKey();
    if (!key.isEmpty())
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=trait.getName();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_NAME_ATTR,XmlWriter.CDATA,name);
    // Icon ID
    int iconId=trait.getIconId();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Static overlay icon ID
    Integer staticOverlayIconId=trait.getStaticIconOverlayId();
    if (staticOverlayIconId!=null)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_STATIC_ICON_OVERLAY_ID_ATTR,XmlWriter.CDATA,staticOverlayIconId.toString());
    }
    // Point cost progression
    ArrayProgression pointCostProgression=trait.getPointCostProgression();
    if (pointCostProgression!=null)
    {
      int pointCostProgressionId=pointCostProgression.getIdentifier();
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_POINT_COST_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(pointCostProgressionId));
    }
    // Rank overlay progression
    ArrayProgression rankOverlayProgression=trait.getRankOverlayProgression();
    if (rankOverlayProgression!=null)
    {
      int rankOverlayProgressionId=rankOverlayProgression.getIdentifier();
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_RANK_OVERLAY_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(rankOverlayProgressionId));
    }
    // Min level
    int minLevel=trait.getMinLevel();
    if (minLevel!=1)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    }
    // Tiers
    int tiers=trait.getTiersCount();
    if (tiers!=1)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TIERS_ATTR,XmlWriter.CDATA,String.valueOf(tiers));
    }
    // Tier property
    String tierPropertyName=trait.getTierPropertyName();
    if (tierPropertyName!=null)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TIER_PROPERTY_ATTR,XmlWriter.CDATA,tierPropertyName);
    }
    // Category
    SkillCategory category=trait.getCategory();
    if (category!=null)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category.getCode()));
    }
    // Nature
    TraitNature nature=trait.getNature();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_NATURE_ATTR,XmlWriter.CDATA,String.valueOf(nature.getCode()));
    // Sub-category
    TraitSubCategory subCategory=trait.getSubCategory();
    if (subCategory!=null)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_SUB_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(subCategory.getCode()));
    }
    // Cosmetic
    boolean cosmetic=trait.isCosmetic();
    if (cosmetic)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_COSMETIC_ATTR,XmlWriter.CDATA,String.valueOf(cosmetic));
    }
    // Tooltip
    String tooltip=trait.getTooltip();
    if (!tooltip.isEmpty())
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TOOLTIP_ATTR,XmlWriter.CDATA,tooltip);
    }
    // Description
    String description=trait.getDescription();
    if (!description.isEmpty())
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }

    hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_TAG,attrs);
    // Stats
    StatsProviderXMLWriter.writeXml(hd,trait.getStatsProvider());
    // Trait groups
    for(TraitGroup traitGroup : trait.getTraitGroups())
    {
      AttributesImpl traitGroupAttrs=new AttributesImpl();
      int code=traitGroup.getCode();
      traitGroupAttrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_GROUP_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
      // Name
      String groupName=traitGroup.getLabel();
      traitGroupAttrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_GROUP_NAME_ATTR,XmlWriter.CDATA,groupName);
      hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_GROUP_TAG,traitGroupAttrs);
      hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_GROUP_TAG);
    }
    // Skills
    for(SkillAtRank skillAtRank : trait.getSkills())
    {
      AttributesImpl skillAttrs=new AttributesImpl();
      // Rank
      int rank=skillAtRank.getRank();
      if (rank>0)
      {
        skillAttrs.addAttribute("","",TraitDescriptionXMLConstants.SKILL_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      }
      SkillDescription skill=skillAtRank.getSkill();
      // ID
      int skillId=skill.getIdentifier();
      skillAttrs.addAttribute("","",TraitDescriptionXMLConstants.SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillId));
      // Name
      String skillName=skill.getName();
      skillAttrs.addAttribute("","",TraitDescriptionXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
      hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_SKILL_TAG,skillAttrs);
      hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_SKILL_TAG);
    }
    // Effects
    // - generators
    for(EffectGenerator generator : trait.getEffectGenerators())
    {
      EffectXMLWriter.writeEffectGenerator(hd,generator,TraitDescriptionXMLConstants.TRAIT_EFFECT_GENERATOR_TAG);
    }
    // - at rank
    for(EffectAtRank effectAtRank : trait.getEffects())
    {
      AttributesImpl effectAttrs=new AttributesImpl();
      // Rank
      int rank=effectAtRank.getRank();
      if (rank>1)
      {
        effectAttrs.addAttribute("","",TraitDescriptionXMLConstants.EFFECT_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      }
      Effect effect=effectAtRank.getEffect();
      // ID
      int effectId=effect.getIdentifier();
      effectAttrs.addAttribute("","",TraitDescriptionXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(effectId));
      // Name
      String effectName=effect.getName();
      effectAttrs.addAttribute("","",TraitDescriptionXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,effectName);
      hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_EFFECT_TAG,effectAttrs);
      hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_EFFECT_TAG);
    }
    // Pre-requisites
    AbstractTraitPrerequisite prerequisite=trait.getTraitPrerequisites();
    if (prerequisite!=null)
    {
      _prerequisitesWriter.writeTraitPrerequisite(hd,prerequisite);
    }
    hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_TAG);
  }
}
