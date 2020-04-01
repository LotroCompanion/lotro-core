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
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;

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
    // Min level
    int minLevel=trait.getMinLevel();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    // Tiers
    int tiers=trait.getTiersCount();
    attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TIERS_ATTR,XmlWriter.CDATA,String.valueOf(tiers));
    // Tier property
    String tierPropertyName=trait.getTierPropertyName();
    if (tierPropertyName!=null)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_TIER_PROPERTY_ATTR,XmlWriter.CDATA,tierPropertyName);
    }
    // Description
    String description=trait.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",TraitDescriptionXMLConstants.TRAIT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }

    hd.startElement("","",TraitDescriptionXMLConstants.TRAIT_TAG,attrs);
    // Stats
    StatsProviderXMLWriter.writeXml(hd,null,trait.getStatsProvider(),null);
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
