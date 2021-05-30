package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.DisenchantmentResult;
import delta.games.lotro.lore.items.Item;

/**
 * Writes disenchantment results to XML files.
 * @author DAM
 */
public class DisenchantmentResultXMLWriter
{
  /**
   * Write a file with disenchantment results.
   * @param toFile Output file.
   * @param disenchantments Disenchantments to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeDisenchantmentsFile(File toFile, List<DisenchantmentResult> disenchantments)
  {
    DisenchantmentResultXMLWriter writer=new DisenchantmentResultXMLWriter();
    boolean ok=writer.writeDisenchantments(toFile,disenchantments,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write disenchantments to a XML file.
   * @param outFile Output file.
   * @param disenchantments Disenchantments to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeDisenchantments(File outFile, final List<DisenchantmentResult> disenchantments, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",DisenchantmentResultXMLConstants.DISENCHANTMENTS_TAG,new AttributesImpl());
        writeDisenchantments(hd,disenchantments);
        hd.endElement("","",DisenchantmentResultXMLConstants.DISENCHANTMENTS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeDisenchantments(TransformerHandler hd, List<DisenchantmentResult> disenchantments) throws Exception
  {
    for(DisenchantmentResult disenchantment : disenchantments)
    {
      writeDisenchantment(hd,disenchantment);
    }
  }

  private void writeDisenchantment(TransformerHandler hd, DisenchantmentResult disenchantment) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=disenchantment.getIdentifier();
    attrs.addAttribute("","",DisenchantmentResultXMLConstants.SOURCE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));

    // Counted item
    CountedItem<Item> countedItem=disenchantment.getCountedItem();
    if (countedItem!=null)
    {
      // ID
      int itemId=countedItem.getId();
      attrs.addAttribute("","",DisenchantmentResultXMLConstants.RESULT_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Name
      String name=countedItem.getName();
      attrs.addAttribute("","",DisenchantmentResultXMLConstants.RESULT_NAME_ATTR,XmlWriter.CDATA,name);
      // Quantity
      int quantity=countedItem.getQuantity();
      attrs.addAttribute("","",DisenchantmentResultXMLConstants.RESULT_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
    }
    // Trophy list
    TrophyList trophyList=disenchantment.getTrophyList();
    if (trophyList!=null)
    {
      int trophyListId=trophyList.getIdentifier();
      attrs.addAttribute("","",DisenchantmentResultXMLConstants.TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(trophyListId));
    }
    hd.startElement("","",DisenchantmentResultXMLConstants.DISENCHANTMENT_TAG,attrs);
    hd.endElement("","",DisenchantmentResultXMLConstants.DISENCHANTMENT_TAG);
  }
}
