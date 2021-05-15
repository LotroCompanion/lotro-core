package delta.games.lotro.lore.items.paper.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Writes paper items to XML files.
 * @author DAM
 */
public class PaperItemsXMLWriter
{
  /**
   * Write some paper items to a XML file.
   * @param toFile File to write to.
   * @param paperItems Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<PaperItem> paperItems)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",PaperItemsXMLConstants.PAPER_ITEMS_TAG,new AttributesImpl());
        for(PaperItem paperItem : paperItems)
        {
          writePaperItem(hd,paperItem);
        }
        hd.endElement("","",PaperItemsXMLConstants.PAPER_ITEMS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writePaperItem(TransformerHandler hd, PaperItem paperItem) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=paperItem.getIdentifier();
    attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=paperItem.getName();
    attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    // Item class
    String itemClass=paperItem.getItemClass();
    attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_CLASS_ATTR,XmlWriter.CDATA,itemClass);
    // Category
    String category=paperItem.getCategory();
    attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_CATEGORY_ATTR,XmlWriter.CDATA,category);
    // Shared
    boolean shared=paperItem.isShared();
    if (shared)
    {
      attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_SHARED_ATTR,XmlWriter.CDATA,String.valueOf(shared));
    }
    // Free
    boolean free=paperItem.isFree();
    if (free)
    {
      attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_FREE_ATTR,XmlWriter.CDATA,String.valueOf(free));
    }
    // Icon ID
    Integer iconId=paperItem.getIconId();
    if (iconId!=null)
    {
      attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_ICON_ID_ATTR,XmlWriter.CDATA,iconId.toString());
    }
    // Cap
    Integer cap=paperItem.getCap();
    if (cap!=null)
    {
      attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_CAP_ATTR,XmlWriter.CDATA,cap.toString());
    }
    // Old
    boolean old=paperItem.isOld();
    if (old)
    {
      attrs.addAttribute("","",PaperItemsXMLConstants.PAPER_ITEM_OLD_ATTR,XmlWriter.CDATA,String.valueOf(old));
    }
    hd.startElement("","",PaperItemsXMLConstants.PAPER_ITEM_TAG,attrs);
    hd.endElement("","",PaperItemsXMLConstants.PAPER_ITEM_TAG);
  }
}
