package delta.games.lotro.lore.items.cosmetics.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.cosmetics.ItemCosmetics;

/**
 * Writes cosmetics data to XML files.
 * @author DAM
 */
public class ItemCosmeticsXMLWriter
{
  /**
   * Write cosmetics data to a XML file.
   * @param toFile File to write to.
   * @param cosmetics Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final ItemCosmetics cosmetics)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCosmetics(hd,cosmetics);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeCosmetics(TransformerHandler hd, ItemCosmetics cosmetics) throws SAXException
  {
    hd.startElement("","",ItemCosmeticsXMLConstants.COSMETICS,new AttributesImpl());
    for(Integer cosmeticID : cosmetics.getCosmeticsIDs())
    {
      AttributesImpl attrs=new AttributesImpl();
      // Cosmetic ID
      attrs.addAttribute("","",ItemCosmeticsXMLConstants.COSMETIC_ID_ATTR,XmlWriter.CDATA,cosmeticID.toString());
      // Item IDs
      int[] itemIDs=cosmetics.findItemIDs(cosmeticID.intValue());
      StringBuilder sb=new StringBuilder();
      for(int itemID : itemIDs)
      {
        if (sb.length()>0)
        {
          sb.append(',');
        }
        sb.append(itemID);
      }
      attrs.addAttribute("","",ItemCosmeticsXMLConstants.COSMETIC_ITEM_IDS_ATTR,XmlWriter.CDATA,sb.toString());
      hd.startElement("","",ItemCosmeticsXMLConstants.COSMETIC_TAG,attrs);
      hd.endElement("","",ItemCosmeticsXMLConstants.COSMETIC_TAG);
    }
    hd.endElement("","",ItemCosmeticsXMLConstants.COSMETICS);
  }
}
