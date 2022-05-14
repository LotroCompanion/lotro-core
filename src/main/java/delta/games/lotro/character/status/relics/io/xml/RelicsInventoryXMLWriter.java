package delta.games.lotro.character.status.relics.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.relics.RelicsInventory;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Writes a relics inventory to an XML file.
 * @author DAM
 */
public class RelicsInventoryXMLWriter
{
  /**
   * Writes a relics inventory to an XML file.
   * @param outFile Output file.
   * @param relicsInventory Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final RelicsInventory relicsInventory, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeInventory(hd,relicsInventory);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a relics inventory to the given XML stream.
   * @param hd XML output stream.
   * @param relicsInventory Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeInventory(TransformerHandler hd, RelicsInventory relicsInventory) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",RelicsInventoryXMLConstants.MAIN_TAG,attrs);
    List<Integer> relicIds=relicsInventory.getRelicIdentifiers();
    RelicsManager relicsMgr=RelicsManager.getInstance();
    for(Integer relicId : relicIds)
    {
      Relic relic=relicsMgr.getById(relicId.intValue());
      AttributesImpl relicAttrs=new AttributesImpl();
      // ID
      relicAttrs.addAttribute("","",RelicsInventoryXMLConstants.RELIC_ID_ATTR,XmlWriter.CDATA,relicId.toString());
      // Name
      String relicName=(relic!=null)?relic.getName():"?";
      relicAttrs.addAttribute("","",RelicsInventoryXMLConstants.RELIC_NAME_ATTR,XmlWriter.CDATA,relicName);
      // Count
      int count=relicsInventory.getRelicCount(relicId.intValue());
      relicAttrs.addAttribute("","",RelicsInventoryXMLConstants.RELIC_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));

      hd.startElement("","",RelicsInventoryXMLConstants.RELIC_TAG,relicAttrs);
      hd.endElement("","",RelicsInventoryXMLConstants.RELIC_TAG);
    }
    hd.endElement("","",RelicsInventoryXMLConstants.MAIN_TAG);
 }
}
