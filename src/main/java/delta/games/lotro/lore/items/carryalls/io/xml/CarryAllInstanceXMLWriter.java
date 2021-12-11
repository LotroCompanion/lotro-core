package delta.games.lotro.lore.items.carryalls.io.xml;

import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.carryalls.CarryAllInstance;

/**
 * Writes carry-alls to an XML stream.
 * @author DAM
 */
public class CarryAllInstanceXMLWriter
{
  /**
   * Write a carry-all to the given XML stream.
   * @param hd XML output stream.
   * @param carryAll Data to write.
   * @throws Exception If an error occurs.
   */
  public static void writeCarryAll(TransformerHandler hd, CarryAllInstance carryAll) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",CarryAllInstanceXMLConstants.CARRY_ALL,attrs);
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
