package delta.games.lotro.character.storage.carryalls.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.carryalls.CarryAll;
import delta.games.lotro.character.storage.carryalls.CarryAllDefinition;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;

/**
 * Writes a carry-all to an XML file.
 * @author DAM
 */
public class CarryAllXMLWriter
{
  /**
   * Write a carry-all to an XML file.
   * @param outFile Output file.
   * @param carryAll Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CarryAll carryAll, String encoding)
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
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a carry-all to the given XML stream.
   * @param hd XML output stream.
   * @param carryAll Data to write.
   * @throws Exception If an error occurs.
   */
  private void writeCarryAll(TransformerHandler hd, CarryAll carryAll) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // ID
    InternalGameId id=carryAll.getInstanceId();
    if (id!=null)
    {
      attrs.addAttribute("","",CarryAllXMLConstants.CARRY_ALL_ID_ATTR,XmlWriter.CDATA,id.asPersistedString());
    }
    // Definition ID
    CarryAllDefinition definition=carryAll.getDefinition();
    if (definition!=null)
    {
      int definitionID=definition.getIdentifier(); 
      attrs.addAttribute("","",CarryAllXMLConstants.CARRY_ALL_DEFINITION_ID_ATTR,XmlWriter.CDATA,String.valueOf(definitionID));
    }
    hd.startElement("","",CarryAllXMLConstants.CARRY_ALL,attrs);
    List<CountedItem<Item>> countedItems=carryAll.getItems();
    for(CountedItem<Item> countedItem : countedItems)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      // ID
      int itemId=countedItem.getId();
      itemAttrs.addAttribute("","",CarryAllXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Name
      String name=countedItem.getName();
      if (name!=null)
      {
        itemAttrs.addAttribute("","",CarryAllXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Count
      int count=countedItem.getQuantity();
      itemAttrs.addAttribute("","",CarryAllXMLConstants.ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      hd.startElement("","",CarryAllXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",CarryAllXMLConstants.ITEM_TAG);
    }
    hd.endElement("","",CarryAllXMLConstants.CARRY_ALL);
  }
}
