package delta.games.lotro.lore.items.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.items.ItemsSet;

/**
 * Writes LOTRO items sets to XML files.
 * @author DAM
 */
public class ItemsSetXMLWriter
{
  /**
   * Write an items set to a XML file.
   * @param outFile Output file.
   * @param set Items set to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final ItemsSet set, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,set);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, ItemsSet set) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    // Key
    String key=set.getKey();
    if (key!=null)
    {
      itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=set.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",ItemsSetXMLConstants.ITEMS_SET_TAG,itemAttrs);

    // Items
    int[] ids=set.getItemIds();
    String[] keys=set.getItemKeys();
    if ((ids!=null) && (keys!=null))
    {
      int nb=Math.min(ids.length,keys.length);
      for(int i=0;i<nb;i++)
      {
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute("","",ItemsSetXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(ids[i]));
        attrs.addAttribute("","",ItemsSetXMLConstants.ITEM_KEY_ATTR,XmlWriter.CDATA,keys[i]);
        hd.startElement("","",ItemsSetXMLConstants.ITEM_TAG,attrs);
        hd.endElement("","",ItemsSetXMLConstants.ITEM_TAG);
      }
    }
    // Bonuses
    int[] nbs=set.getNumberOfItemsForBonuses();
    if (nbs!=null)
    {
      for(int nb : nbs)
      {
        String[] bonuses=set.getBonus(nb);
        for(String bonus : bonuses)
        {
          AttributesImpl attrs=new AttributesImpl();
          attrs.addAttribute("","",ItemsSetXMLConstants.BONUS_NB_ITEMS_ATTR,XmlWriter.CDATA,String.valueOf(nb));
          attrs.addAttribute("","",ItemsSetXMLConstants.BONUS_VALUE_ATTR,XmlWriter.CDATA,bonus);
          hd.startElement("","",ItemsSetXMLConstants.BONUS_TAG,attrs);
          hd.endElement("","",ItemsSetXMLConstants.BONUS_TAG);
        }
      }
    }
    hd.endElement("","",ItemsSetXMLConstants.ITEMS_SET_TAG);
  }
}
