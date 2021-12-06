package delta.games.lotro.lore.consumables.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.consumables.Consumable;

/**
 * Writes consumables to XML files.
 * @author DAM
 */
public class ConsumableXMLWriter
{
  /**
   * Write some consumables to a XML file.
   * @param toFile File to write to.
   * @param consumables Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Consumable> consumables)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ConsumableXMLConstants.CONSUMABLES_TAG,new AttributesImpl());
        for(Consumable consumable : consumables)
        {
          writeConsumable(hd,consumable);
        }
        hd.endElement("","",ConsumableXMLConstants.CONSUMABLES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeConsumable(TransformerHandler hd, Consumable consumable) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=consumable.getIdentifier();
    attrs.addAttribute("","",ConsumableXMLConstants.CONSUMABLE_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=consumable.getName();
    attrs.addAttribute("","",ConsumableXMLConstants.CONSUMABLE_NAME_ATTR,XmlWriter.CDATA,name);
    // Icon ID
    String iconId=consumable.getIcon();
    attrs.addAttribute("","",ConsumableXMLConstants.CONSUMABLE_ICON_ID_ATTR,XmlWriter.CDATA,iconId);
    // Item class
    ItemClass itemClass=consumable.getItemClass();
    if (itemClass!=null)
    {
      attrs.addAttribute("","",ConsumableXMLConstants.CONSUMABLE_CLASS_ATTR,XmlWriter.CDATA,String.valueOf(itemClass.getCode()));
    }

    hd.startElement("","",ConsumableXMLConstants.CONSUMABLE_TAG,attrs);
    // Stats
    StatsProviderXMLWriter.writeXml(hd,null,consumable.getProvider(),null);
    hd.endElement("","",ConsumableXMLConstants.CONSUMABLE_TAG);
  }
}
