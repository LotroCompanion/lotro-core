package delta.games.lotro.common.blacklist.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.blacklist.Blacklist;

/**
 * Writes a blacklist to an XML file.
 * @author DAM
 */
public class BlacklistXMLWriter
{
  /**
   * Write a blacklist to an XML file.
   * @param outFile Output file.
   * @param blacklist Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final Blacklist blacklist, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeBlacklist(hd,blacklist);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a blacklist to the given XML stream.
   * @param hd XML output stream.
   * @param blacklist Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeBlacklist(TransformerHandler hd, Blacklist blacklist) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",BlacklistXMLConstants.BLACKLIST_TAG,attrs);

    List<Integer> ids=blacklist.getAllBLacklistedIDs();
    for(Integer id : ids)
    {
      AttributesImpl elementAttrs=new AttributesImpl();
      // ID
      elementAttrs.addAttribute("","",BlacklistXMLConstants.ELEMENT_ID_ATTR,XmlWriter.CDATA,id.toString());
      // Name
      //String name=emote.getName();
      //elementAttrs.addAttribute("","",BlacklistXMLConstants.EMOTE_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",BlacklistXMLConstants.ELEMENT,elementAttrs);
      hd.endElement("","",BlacklistXMLConstants.ELEMENT);
    }
    hd.endElement("","",BlacklistXMLConstants.BLACKLIST_TAG);
  }
}
