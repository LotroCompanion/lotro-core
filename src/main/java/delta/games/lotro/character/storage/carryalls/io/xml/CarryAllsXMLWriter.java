package delta.games.lotro.character.storage.carryalls.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.carryalls.CarryAll;
import delta.games.lotro.character.storage.carryalls.CarryAllDefinition;
import delta.games.lotro.character.storage.carryalls.CarryAllsManager;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;

/**
 * Writes carry-alls to an XML file.
 * @author DAM
 */
public class CarryAllsXMLWriter
{
  /**
   * Write some carry-alls to an XML file.
   * @param outFile Output file.
   * @param carryAllsManager Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CarryAllsManager carryAllsManager, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCarryAlls(hd,carryAllsManager);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeCarryAlls(TransformerHandler hd, CarryAllsManager carryAllsManager) throws Exception
  {
    hd.startElement("","",CarryAllsXMLConstants.CARRY_ALLS,new AttributesImpl());
    List<String> keys=carryAllsManager.getKeys();
    for(String key : keys)
    {
      CarryAll carryAll=carryAllsManager.getCarryAllByKey(key);
      writeCarryAll(hd,carryAll);
    }
    hd.endElement("","",CarryAllsXMLConstants.CARRY_ALLS);
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
      attrs.addAttribute("","",CarryAllsXMLConstants.CARRY_ALL_ID_ATTR,XmlWriter.CDATA,id.asPersistedString());
    }
    // Definition ID
    CarryAllDefinition definition=carryAll.getDefinition();
    if (definition!=null)
    {
      int definitionID=definition.getIdentifier(); 
      attrs.addAttribute("","",CarryAllsXMLConstants.CARRY_ALL_DEFINITION_ID_ATTR,XmlWriter.CDATA,String.valueOf(definitionID));
    }
    hd.startElement("","",CarryAllsXMLConstants.CARRY_ALL,attrs);
    List<CountedItem<Item>> countedItems=carryAll.getItems();
    for(CountedItem<Item> countedItem : countedItems)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      // ID
      int itemId=countedItem.getId();
      itemAttrs.addAttribute("","",CarryAllsXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Name
      String name=countedItem.getName();
      if (name!=null)
      {
        itemAttrs.addAttribute("","",CarryAllsXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Count
      int count=countedItem.getQuantity();
      itemAttrs.addAttribute("","",CarryAllsXMLConstants.ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      hd.startElement("","",CarryAllsXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",CarryAllsXMLConstants.ITEM_TAG);
    }
    hd.endElement("","",CarryAllsXMLConstants.CARRY_ALL);
  }
}
