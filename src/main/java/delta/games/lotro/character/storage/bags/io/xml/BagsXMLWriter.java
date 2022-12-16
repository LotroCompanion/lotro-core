package delta.games.lotro.character.storage.bags.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.BagsSetup;
import delta.games.lotro.character.storage.bags.SingleBagSetup;
import delta.games.lotro.common.status.io.xml.StatusMetadataIO;
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
    // Status
    StatusMetadataIO.writeStatusMetadata(hd,bagsManager.getStatusMetadata());
    // Layout
    writeLayout(hd,bagsManager.getBagsSetup());
    // Slots
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

  private void writeLayout(TransformerHandler hd, BagsSetup setup) throws SAXException
  {
    for(Integer bagIndex : setup.getBagIndexes())
    {
      SingleBagSetup bagSetup=setup.getBagSetup(bagIndex.intValue());
      AttributesImpl attrs=new AttributesImpl();
      // Bag index
      attrs.addAttribute("","",BagsXMLConstants.SETUP_BAG_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(bagIndex));
      // Bag size
      int bagSize=bagSetup.getSize();
      attrs.addAttribute("","",BagsXMLConstants.SETUP_BAG_SIZE_ATTR,XmlWriter.CDATA,String.valueOf(bagSize));
      // Bag width
      int bagWidth=bagSetup.getWidth();
      attrs.addAttribute("","",BagsXMLConstants.SETUP_BAG_WIDTH_ATTR,XmlWriter.CDATA,String.valueOf(bagWidth));
      hd.startElement("","",BagsXMLConstants.SETUP_TAG,attrs);
      // Slots
      int size=bagSetup.getSize();
      for(int i=0;i<size;i++)
      {
        Integer itemIndex=bagSetup.getItemIndexAt(i);
        if (itemIndex==null)
        {
          continue;
        }
        AttributesImpl slotAttrs=new AttributesImpl();
        // slot index
        slotAttrs.addAttribute("","",BagsXMLConstants.SLOT_SETUP_SLOT_INDEX_ATTR,CDATA,String.valueOf(i));
        // item index
        slotAttrs.addAttribute("","",BagsXMLConstants.SLOT_SETUP_ITEM_INDEX_ATTR,CDATA,itemIndex.toString());
        hd.startElement("","",BagsXMLConstants.SLOT_SETUP_TAG,slotAttrs);
        hd.endElement("","",BagsXMLConstants.SLOT_SETUP_TAG);
      }
      hd.endElement("","",BagsXMLConstants.SETUP_TAG);
    }
  }
}
