package delta.games.lotro.lore.items.legendary2.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary2.EnhancementRune;

/**
 * Writes enhancement runes to XML documents.
 * @author DAM
 */
public class EnhancementRunesXMLWriter
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EnhancementRunesXMLWriter.class);

  /**
   * Write some enhancement runes to a XML file.
   * @param toFile File to write to.
   * @param enhancementRunes Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<EnhancementRune> enhancementRunes)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EnhancementRunesXMLConstants.ENHANCEMENT_RUNES_TAG,new AttributesImpl());
        for(EnhancementRune enhancementRune : enhancementRunes)
        {
          write(hd,enhancementRune);
        }
        hd.endElement("","",EnhancementRunesXMLConstants.ENHANCEMENT_RUNES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a enhancement rune to the given XML stream.
   * @param hd XML output stream.
   * @param enhancementRune Rune to write.
   * @throws SAXException If an error occurs.
   */
  private static void write(TransformerHandler hd, EnhancementRune enhancementRune) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Item item=enhancementRune.getItem();
    if (item==null)
    {
      LOGGER.warn("Cannot write an enhancement rune with no item!");
      return;
    }
    // Item ID
    int itemID=item.getIdentifier();
    attrs.addAttribute("","",EnhancementRunesXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
    // Name
    String name=item.getName();
    attrs.addAttribute("","",EnhancementRunesXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Min item level
    int minItemLevel=enhancementRune.getMinItemLevel();
    attrs.addAttribute("","",EnhancementRunesXMLConstants.MIN_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minItemLevel));
    // Max item level
    int maxItemLevel=enhancementRune.getMaxItemLevel();
    attrs.addAttribute("","",EnhancementRunesXMLConstants.MAX_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxItemLevel));
    // Increment
    int increment=enhancementRune.getLevelUpIncrement();
    if (increment!=1)
    {
      attrs.addAttribute("","",EnhancementRunesXMLConstants.LEVEL_INCREMENT_ATTR,XmlWriter.CDATA,String.valueOf(increment));
    }
    hd.startElement("","",EnhancementRunesXMLConstants.ENHANCEMENT_RUNE_TAG,attrs);
    hd.endElement("","",EnhancementRunesXMLConstants.ENHANCEMENT_RUNE_TAG);
  }
}
