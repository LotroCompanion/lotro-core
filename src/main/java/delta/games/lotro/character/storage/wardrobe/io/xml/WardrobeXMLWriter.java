package delta.games.lotro.character.storage.wardrobe.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.wardrobe.Wardrobe;
import delta.games.lotro.character.storage.wardrobe.WardrobeItem;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Writes a wardrobe to and XML document.
 * @author DAM
 */
public class WardrobeXMLWriter
{
  /**
   * Write a wardrobe to a XML file.
   * @param outFile Output file.
   * @param wardrobe Wardrobe to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final Wardrobe wardrobe, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",WardrobeXMLConstants.WARDROBE_TAG,attrs);
        write(hd,wardrobe);
        hd.endElement("","",WardrobeXMLConstants.WARDROBE_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a wardrobe.
   * @param hd Output stream.
   * @param wardrobe Wardrobe to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, Wardrobe wardrobe) throws Exception
  {
    // Elements
    List<WardrobeItem> elements=wardrobe.getAll();
    for(WardrobeItem element : elements)
    {
      writeWardrobeElement(hd,element);
    }
  }

  private static void writeWardrobeElement(TransformerHandler hd, WardrobeItem element) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    Item item=element.getItem();
    // Item ID
    if (item==null)
    {
      return;
    }
    // Item ID
    int itemId=item.getIdentifier();
    attrs.addAttribute("","",WardrobeXMLConstants.ELEMENT_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
    // Item name
    String itemName=item.getName();
    if (itemName!=null)
    {
      attrs.addAttribute("","",WardrobeXMLConstants.ELEMENT_ITEM_NAME_ATTR,XmlWriter.CDATA,itemName);
    }
    hd.startElement("","",WardrobeXMLConstants.WARDROBE_ITEM_TAG,attrs);
    // Colors
    List<ColorDescription> colors=element.getColors();
    for(ColorDescription color : colors)
    {
      AttributesImpl colorAttrs=new AttributesImpl();
      // Color code
      int colorCode=color.getIntCode();
      colorAttrs.addAttribute("","",WardrobeXMLConstants.COLOR_CODE_ATTR,XmlWriter.CDATA,String.valueOf(colorCode));
      // Color name
      String colorName=color.getName();
      if (colorName!=null)
      {
        colorAttrs.addAttribute("","",WardrobeXMLConstants.COLOR_NAME_ATTR,XmlWriter.CDATA,colorName);
      }
      hd.startElement("","",WardrobeXMLConstants.COLOR_TAG,colorAttrs);
      hd.endElement("","",WardrobeXMLConstants.COLOR_TAG);
    }
    hd.endElement("","",WardrobeXMLConstants.WARDROBE_ITEM_TAG);
  }
}
