package delta.games.lotro.character.storage.bags.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;

/**
 * Writes a bags manager to an XML file.
 * @author DAM
 */
public class BagsXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a bags manager to an XML file.
   * @param outFile Output file.
   * @param bagsManager Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final BagsManager bagsManager, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeBags(hd,bagsManager);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a bags manager to the given XML stream.
   * @param hd XML output stream.
   * @param bagsManager Data to write.
   * @throws Exception If an error occurs.
   */
  private void writeBags(TransformerHandler hd, BagsManager bagsManager) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Max
    int max=bagsManager.getCapacity();
    attrs.addAttribute("","",BagsXMLConstants.BAGS_MAX_ATTR,XmlWriter.CDATA,String.valueOf(max));
    hd.startElement("","",BagsXMLConstants.BAGS_TAG,attrs);

    ItemXMLWriter writer=new ItemXMLWriter();
    for(Integer index : bagsManager.getIndexes())
    {
      CountedItem<ItemInstance<? extends Item>> countedItemInstance=bagsManager.getSlotContent(index.intValue());
      AttributesImpl slotAttrs=new AttributesImpl();
      slotAttrs.addAttribute("","",BagsXMLConstants.SLOT_INDEX_ATTR,CDATA,index.toString());
      int count=countedItemInstance.getQuantity();
      if (count>1)
      {
        slotAttrs.addAttribute("","",BagsXMLConstants.SLOT_COUNT_ATTR,CDATA,String.valueOf(count));
      }
      hd.startElement("","",BagsXMLConstants.SLOT_TAG,slotAttrs);
      ItemInstance<? extends Item> itemInstance=countedItemInstance.getManagedItem();
      writer.writeItemInstance(hd,itemInstance);
      hd.endElement("","",BagsXMLConstants.SLOT_TAG);
    }
    hd.endElement("","",BagsXMLConstants.BAGS_TAG);
  }
}
