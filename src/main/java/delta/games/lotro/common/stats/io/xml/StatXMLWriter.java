package delta.games.lotro.common.stats.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Writes stat definitions to XML files.
 * @author DAM
 */
public class StatXMLWriter
{
  /**
   * Write some stat definitions to a XML file.
   * @param toFile File to write to.
   * @param descriptions Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<StatDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",StatXMLConstants.STATS_TAG,new AttributesImpl());
        for(StatDescription description : descriptions)
        {
          writeStatDescription(hd,description);
        }
        hd.endElement("","",StatXMLConstants.STATS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeStatDescription(TransformerHandler hd, StatDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // ID
    int id=description.getIdentifier();
    attrs.addAttribute("","",StatXMLConstants.STAT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=description.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",StatXMLConstants.STAT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Key
    String key=description.getKey();
    if (key!=null)
    {
      attrs.addAttribute("","",StatXMLConstants.STAT_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Legacy key
    String legacyKey=description.getLegacyKey();
    if (legacyKey!=null)
    {
      attrs.addAttribute("","",StatXMLConstants.STAT_LEGACY_KEY_ATTR,XmlWriter.CDATA,legacyKey);
    }
    // Is percentage
    boolean isPercentage=description.isPercentage();
    if (isPercentage)
    {
      attrs.addAttribute("","",StatXMLConstants.STAT_IS_PERCENTAGE_ATTR,XmlWriter.CDATA,String.valueOf(isPercentage));
    }
    hd.startElement("","",StatXMLConstants.STAT_TAG,attrs);
    hd.endElement("","",StatXMLConstants.STAT_TAG);
  }
}
