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
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.utils.maths.ArrayProgression;

/**
 * Writes traits to XML files.
 * @author DAM
 */
public class TraitDescriptionXMLWriter
{
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
        hd.startElement("","",TraitDescriptionXMLConstants.TRAITS_TAG,new AttributesImpl());
        for(TraitDescription trait : traits)
        {
          writeTrait(hd,trait);
        }
        hd.endElement("","",TraitDescriptionXMLConstants.TRAITS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeTrait(TransformerHandler hd, TraitDescription trait) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=trait.getIdentifier();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=trait.getKey();
    if (key.length()>0)
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
    // Cosmetic
    boolean cosmetic=trait.isCosmetic();
    if (cosmetic)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_COSMETIC_ATTR,XmlWriter.CDATA,String.valueOf(cosmetic));
    }
    // Tooltip
    String tooltip=trait.getTooltip();
    if (tooltip.length()>0)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TOOLTIP_ATTR,XmlWriter.CDATA,tooltip);
    }
    // Description
    String description=trait.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }

    hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_TAG,attrs);
    // Stats
    StatsProviderXMLWriter.writeXml(hd,trait.getStatsProvider());
    // Skills
    for(SkillDescription skill : trait.getSkills())
    {
      AttributesImpl skillAttrs=new AttributesImpl();
      int skillId=skill.getIdentifier();
      skillAttrs.addAttribute("","",TraitDescriptionXMLConstants.SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillId));
      // Name
      String skillName=skill.getName();
      skillAttrs.addAttribute("","",TraitDescriptionXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
      hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_SKILL_TAG,skillAttrs);
      hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_SKILL_TAG);
    }
    hd.endElement("","",TraitDescriptionXMLConstants.TRAIT_TAG);
  }
}
