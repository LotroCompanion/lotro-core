package delta.games.lotro.common.status.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.status.StatusMetadata;

/**
 * I/O methods for status metadata.
 * @author DAM
 */
public class StatusMetadataIO
{
  /**
   * Load status metadata.
   * @param root Parent tag.
   * @param storage Storage.
   */
  public static void parseStatusMetadata(Element root, StatusMetadata storage)
  {
    Element statusTag=DOMParsingTools.getChildTagByName(root,StatusMetadataXMLConstants.STATUS_TAG);
    if (statusTag!=null)
    {
      NamedNodeMap attrs=statusTag.getAttributes();
      long timestamp=DOMParsingTools.getLongAttribute(attrs,StatusMetadataXMLConstants.STATUS_TIMESTAMP_ATTR,0);
      storage.setTimeStamp(timestamp);
    }
  }

  /**
   * Write status metadata.
   * @param hd Output stream.
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeStatusMetadata(TransformerHandler hd, StatusMetadata data) throws SAXException
  {
    if ((data!=null) && (!data.isEmpty()))
    {
      AttributesImpl attrs=new AttributesImpl();
      long timestamp=data.getTimeStamp();
      attrs.addAttribute("","",StatusMetadataXMLConstants.STATUS_TIMESTAMP_ATTR,XmlWriter.CDATA,String.valueOf(timestamp));
      hd.startElement("","",StatusMetadataXMLConstants.STATUS_TAG,attrs);
      hd.endElement("","",StatusMetadataXMLConstants.STATUS_TAG);
    }
  }
}
