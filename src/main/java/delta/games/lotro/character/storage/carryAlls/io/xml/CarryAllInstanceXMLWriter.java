package delta.games.lotro.character.storage.carryAlls.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.status.io.xml.StatusMetadataIO;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.carryalls.CarryAll;

/**
 * Writes carry-alls to an XML stream.
 * @author DAM
 */
public class CarryAllInstanceXMLWriter
{
  /**
   * Write a carry-all instance to an XML file.
   * @param outFile Output file.
   * @param carryAll Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File outFile, final CarryAllInstance carryAll)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCarryAll(hd,carryAll);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a carry-all to the given XML stream.
   * @param hd XML output stream.
   * @param carryAll Data to write.
   * @throws SAXException If an error occurs.
   */
  private static void writeCarryAll(TransformerHandler hd, CarryAllInstance carryAll) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Item ID
    CarryAll carryAllItem=carryAll.getReference();
    if (carryAllItem!=null)
    {
      int carryAllItemID=carryAllItem.getIdentifier();
      attrs.addAttribute("","",CarryAllInstanceXMLConstants.CARRY_ALL_ITEM_ID_TAG,XmlWriter.CDATA,String.valueOf(carryAllItemID));
    }
    hd.startElement("","",CarryAllInstanceXMLConstants.CARRY_ALL,attrs);
    // Status
    StatusMetadataIO.writeStatusMetadata(hd,carryAll.getStatusMetadata());
    // Items
    List<CountedItem<Item>> countedItems=carryAll.getItems();
    Collections.sort(countedItems,new IdentifiableComparator<CountedItem<Item>>());
    for(CountedItem<Item> countedItem : countedItems)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      // ID
      int itemId=countedItem.getId();
      itemAttrs.addAttribute("","",CarryAllInstanceXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Name
      String name=countedItem.getName();
      if (name!=null)
      {
        itemAttrs.addAttribute("","",CarryAllInstanceXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Count
      int count=countedItem.getQuantity();
      itemAttrs.addAttribute("","",CarryAllInstanceXMLConstants.ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      hd.startElement("","",CarryAllInstanceXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",CarryAllInstanceXMLConstants.ITEM_TAG);
    }
    hd.endElement("","",CarryAllInstanceXMLConstants.CARRY_ALL);
  }
}
