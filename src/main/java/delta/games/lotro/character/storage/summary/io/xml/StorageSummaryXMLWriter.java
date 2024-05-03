package delta.games.lotro.character.storage.summary.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.summary.CharacterStorageSummary;
import delta.games.lotro.character.storage.summary.SingleStorageSummary;

/**
 * Writes a storage summary to an XML file.
 * @author DAM
 */
public class StorageSummaryXMLWriter
{
  /**
   * Write a storage summary to an XML file.
   * @param outFile Output file.
   * @param summary Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterStorageSummary summary, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStorageSummary(hd,summary);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeStorageSummary(TransformerHandler hd, CharacterStorageSummary summary) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",StorageSummaryXMLConstants.CHARACTER_STORAGE_SUMMARY_TAG,attrs);
    writeSingleStorageSummary(hd,summary.getBags(),StorageSummaryXMLConstants.BAGS_TAG);
    writeSingleStorageSummary(hd,summary.getOwnVault(),StorageSummaryXMLConstants.OWN_VAULT_TAG);
    hd.endElement("","",StorageSummaryXMLConstants.CHARACTER_STORAGE_SUMMARY_TAG);
  }

  private void writeSingleStorageSummary(TransformerHandler hd, SingleStorageSummary summary, String tagName) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Available
    int available=summary.getAvailable();
    attrs.addAttribute("","",StorageSummaryXMLConstants.AVAILABLE_ATTR,XmlWriter.CDATA,String.valueOf(available));
    // Max
    int max=summary.getMax();
    attrs.addAttribute("","",StorageSummaryXMLConstants.MAX_ATTR,XmlWriter.CDATA,String.valueOf(max));
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }
}
