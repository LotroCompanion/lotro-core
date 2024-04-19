package delta.games.lotro.character.xp.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.xp.XPTable;

/**
 * Writes XP tables to XML files.
 * @author DAM
 */
public class XPTableXMLWriter
{
  /**
   * Write an XP table to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final XPTable data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeXPTable(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeXPTable(TransformerHandler hd, XPTable data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",XPTableXMLConstants.XP_TABLE,attrs);
    // Write XP table
    long[] xpTable=data.getXpTable();
    if (xpTable!=null)
    {
      for(int i=1;i<xpTable.length;i++)
      {
        AttributesImpl xpAttrs=new AttributesImpl();
        // Legendary level
        xpAttrs.addAttribute("","",XPTableXMLConstants.XP_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(i));
        // XP
        long xp=xpTable[i];
        xpAttrs.addAttribute("","",XPTableXMLConstants.XP_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(xp));
        hd.startElement("","",XPTableXMLConstants.ENTRY_TAG,xpAttrs);
        hd.endElement("","",XPTableXMLConstants.ENTRY_TAG);
      }
    }
    hd.endElement("","",XPTableXMLConstants.XP_TABLE);
  }
}
