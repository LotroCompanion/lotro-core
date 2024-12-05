package delta.games.lotro.lore.perks.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.PerkUICategory;
import delta.games.lotro.lore.perks.PerkDescription;

/**
 * Writes hobbies to XML files.
 * @author DAM
 */
public class PerkDescriptionXMLWriter
{
  /**
   * Write some perks to a XML file.
   * @param toFile File to write to.
   * @param perks Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<PerkDescription> perks)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",PerkDescriptionXMLConstants.PERKS_TAG,new AttributesImpl());
        for(PerkDescription perk : perks)
        {
          writePerk(hd,perk);
        }
        hd.endElement("","",PerkDescriptionXMLConstants.PERKS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writePerk(TransformerHandler hd, PerkDescription perk) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=perk.getIdentifier();
    attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=perk.getName();
    attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_NAME_ATTR,XmlWriter.CDATA,name);
    // Description
    String description=perk.getDescription();
    if (!description.isEmpty())
    {
      attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Icon ID
    int iconId=perk.getIconId();
    attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Effect
    Effect effect=perk.getEffect();
    if (effect!=null)
    {
      int effectId=effect.getIdentifier();
      attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(effectId));
    }
    // Category
    PerkUICategory category=perk.getUICategory();
    if (category!=null)
    {
      int categoryCode=category.getCode();
      attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_UI_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(categoryCode));
    }
    // Min level
    int minLevel=perk.getMinLevel();
    attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    // Points cost
    int pointsCost=perk.getPointsCost();
    if (pointsCost>0)
    {
      attrs.addAttribute("","",PerkDescriptionXMLConstants.PERK_POINTS_COST_ATTR,XmlWriter.CDATA,String.valueOf(pointsCost));
    }
    hd.startElement("","",PerkDescriptionXMLConstants.PERK_TAG,attrs);
    hd.endElement("","",PerkDescriptionXMLConstants.PERK_TAG);
  }
}
