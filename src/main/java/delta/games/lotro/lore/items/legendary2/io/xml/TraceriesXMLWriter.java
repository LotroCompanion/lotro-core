package delta.games.lotro.lore.items.legendary2.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Writes traceries to XML documents.
 * @author DAM
 */
public class TraceriesXMLWriter
{
  private static final Logger LOGGER=Logger.getLogger(TraceriesXMLWriter.class);

  /**
   * Write some traceries to a XML file.
   * @param toFile File to write to.
   * @param traceries Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Tracery> traceries)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",TraceriesXMLConstants.TRACERIES_TAG,new AttributesImpl());
        for(Tracery tracery : traceries)
        {
          write(hd,tracery);
        }
        hd.endElement("","",TraceriesXMLConstants.TRACERIES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a tracery to the given XML stream.
   * @param hd XML output stream.
   * @param tracery Tracery to write.
   * @throws SAXException If an error occurs.
   */
  private static void write(TransformerHandler hd, Tracery tracery) throws SAXException
  {
    AttributesImpl traceryAttrs=new AttributesImpl();
    Item item=tracery.getItem();
    if (item==null)
    {
      LOGGER.warn("Cannot write a tracery with no item!");
      return;
    }
    // Item ID
    int itemID=item.getIdentifier();
    traceryAttrs.addAttribute("","",TraceriesXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
    // Name
    String name=item.getName();
    traceryAttrs.addAttribute("","",TraceriesXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Type
    SocketType type=tracery.getType();
    int typeCode=type.getCode();
    traceryAttrs.addAttribute("","",TraceriesXMLConstants.SOCKET_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(typeCode));
    // Min item level
    int minItemLevel=tracery.getMinItemLevel();
    traceryAttrs.addAttribute("","",TraceriesXMLConstants.MIN_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minItemLevel));
    // Max item level
    int maxItemLevel=tracery.getMaxItemLevel();
    traceryAttrs.addAttribute("","",TraceriesXMLConstants.MAX_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxItemLevel));
    // Increment
    int increment=tracery.getLevelUpIncrement();
    if (increment!=1)
    {
      traceryAttrs.addAttribute("","",TraceriesXMLConstants.LEVEL_INCREMENT_ATTR,XmlWriter.CDATA,String.valueOf(increment));
    }
    // Set ID
    int setId=tracery.getSetId();
    if (setId!=0)
    {
      traceryAttrs.addAttribute("","",TraceriesXMLConstants.SET_ID_ATTR,XmlWriter.CDATA,String.valueOf(setId));
    }
    // Uniqueness channel
    String uniquenessChannel=tracery.getUniquenessChannel();
    if (uniquenessChannel!=null)
    {
      traceryAttrs.addAttribute("","",TraceriesXMLConstants.UNIQUENESS_CHANNEL_ATTR,XmlWriter.CDATA,uniquenessChannel);
    }
    hd.startElement("","",TraceriesXMLConstants.TRACERY_TAG,traceryAttrs);
    hd.endElement("","",TraceriesXMLConstants.TRACERY_TAG);
  }
}
