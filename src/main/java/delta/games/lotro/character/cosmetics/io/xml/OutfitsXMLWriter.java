package delta.games.lotro.character.cosmetics.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.cosmetics.Outfit;
import delta.games.lotro.character.cosmetics.OutfitElement;
import delta.games.lotro.character.cosmetics.OutfitsConstants;
import delta.games.lotro.character.cosmetics.OutfitsManager;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Writes outfits to and XML document.
 * @author DAM
 */
public class OutfitsXMLWriter
{
  /**
   * Write an outfits manager to a XML file.
   * @param outFile Output file.
   * @param outfitsMgr Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final OutfitsManager outfitsMgr, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,outfitsMgr);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write an outfits manager.
   * @param hd Output stream.
   * @param outfitsMgr Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, OutfitsManager outfitsMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int currentIndex=outfitsMgr.getCurrentOutfitIndex();
    attrs.addAttribute("","",OutfitsXMLConstants.OUTFITS_CURRENT_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(currentIndex));
    hd.startElement("","",OutfitsXMLConstants.OUTFITS_TAG,attrs);
    for(Integer outfitIndex : outfitsMgr.getOutfitIndexes())
    {
      Outfit outfit=outfitsMgr.getOutfit(outfitIndex.intValue());
      writeOutfit(hd,outfitIndex.intValue(),outfit);
    }
    hd.endElement("","",OutfitsXMLConstants.OUTFITS_TAG);
  }

  private static void writeOutfit(TransformerHandler hd, int index, Outfit outfit) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    attrs.addAttribute("","",OutfitsXMLConstants.OUTFIT_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(index));
    hd.startElement("","",OutfitsXMLConstants.OUTFIT_TAG,attrs);
    for(GearSlot slot : OutfitsConstants.getAll())
    {
      boolean visible=outfit.isSlotVisible(slot);
      OutfitElement element=outfit.getSlot(slot);
      writeOutfitElement(hd,slot,visible,element);
    }
    hd.endElement("","",OutfitsXMLConstants.OUTFIT_TAG);
  }

  private static void writeOutfitElement(TransformerHandler hd, GearSlot slot, boolean visible, OutfitElement element) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Slot
    attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_SLOT_ATTR,XmlWriter.CDATA,slot.getKey());
    // Visible
    attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_VISIBLE_ATTR,XmlWriter.CDATA,String.valueOf(visible));
    if (element!=null)
    {
      Item item=element.getItem();
      // Item ID
      if (item!=null)
      {
        // Item ID
        int itemId=item.getIdentifier();
        attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
        // Item name
        String itemName=item.getName();
        if (itemName!=null)
        {
          attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_ITEM_NAME_ATTR,XmlWriter.CDATA,itemName);
        }
      }
      // Color
      ColorDescription color=element.getColor();
      if (color!=null)
      {
        // Color code
        int colorCode=color.getIntCode();
        attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_COLOR_CODE,XmlWriter.CDATA,String.valueOf(colorCode));
        // Color name
        String colorName=color.getName();
        if (colorName!=null)
        {
          attrs.addAttribute("","",OutfitsXMLConstants.ELEMENT_COLOR_NAME,XmlWriter.CDATA,colorName);
        }
      }
    }
    hd.startElement("","",OutfitsXMLConstants.ELEMENT_TAG,attrs);
    hd.endElement("","",OutfitsXMLConstants.ELEMENT_TAG);
  }
}
