package delta.games.lotro.lore.items.effects.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.io.xml.EffectXMLWriter;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.lore.items.effects.GenericItemEffects;

/**
 * Writes generic item effects to XML documents.
 * @author DAM
 */
public class GenericItemEffectsXMLWriter
{
  /**
   * Write generic item effects to a XML file.
   * @param toFile File to write to.
   * @param allItemEffects Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<GenericItemEffects> allItemEffects)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",GenericItemEffectsXMLConstants.GENERIC_ITEM_EFFECTS_TAG,new AttributesImpl());
        for(GenericItemEffects itemEffects : allItemEffects)
        {
          writeGenericItemEffects(hd,itemEffects);
        }
        hd.endElement("","",GenericItemEffectsXMLConstants.GENERIC_ITEM_EFFECTS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write an item effects to a XML document.
   * @param hd Output.
   * @param itemEffects Data to write.
   * @throws SAXException If an error occurs.
   */
  private static void writeGenericItemEffects(TransformerHandler hd, GenericItemEffects itemEffects) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Category
    EquipmentCategory category=itemEffects.getCategory();
    int code=category.getCode();
    attrs.addAttribute("","",GenericItemEffectsXMLConstants.CATEGORY_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    hd.startElement("","",GenericItemEffectsXMLConstants.CATEGORY_TAG,attrs);
    // Effects
    for(Effect effect : itemEffects.getEffects())
    {
      EffectXMLWriter.writeEffect(hd,effect);
    }
    hd.endElement("","",GenericItemEffectsXMLConstants.CATEGORY_TAG);
  }
}
