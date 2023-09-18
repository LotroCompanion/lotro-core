package delta.games.lotro.lore.items.essences.io.xml;

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
import delta.games.lotro.lore.items.essences.Essence;

/**
 * Writes essences to XML documents.
 * @author DAM
 */
public class EssencesXMLWriter
{
  private static final Logger LOGGER=Logger.getLogger(EssencesXMLWriter.class);

  /**
   * Write some essences to a XML file.
   * @param toFile File to write to.
   * @param essences Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Essence> essences)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EssencesXMLConstants.ESSENCES_TAG,new AttributesImpl());
        for(Essence essence : essences)
        {
          write(hd,essence);
        }
        hd.endElement("","",EssencesXMLConstants.ESSENCES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write an essence to the given XML stream.
   * @param hd XML output stream.
   * @param essence Essence to write.
   * @throws SAXException If an error occurs.
   */
  private static void write(TransformerHandler hd, Essence essence) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Item item=essence.getItem();
    if (item==null)
    {
      LOGGER.warn("Cannot write an essence with no item!");
      return;
    }
    // ID
    int id=item.getIdentifier();
    attrs.addAttribute("","",EssencesXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=item.getName();
    attrs.addAttribute("","",EssencesXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Type
    SocketType type=essence.getType();
    int typeCode=type.getCode();
    attrs.addAttribute("","",EssencesXMLConstants.SOCKET_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(typeCode));
    // Tier
    Integer tier=essence.getTier();
    if (tier!=null)
    {
      attrs.addAttribute("","",EssencesXMLConstants.TIER_ATTR,XmlWriter.CDATA,tier.toString());
    }
    // Uniqueness channel
    /*
    ItemUniquenessChannel uniquenessChannel=essence.getUniquenessChannel();
    if (uniquenessChannel!=null)
    {
      attrs.addAttribute("","",EssencesXMLConstants.UNIQUENESS_CHANNEL_ATTR,XmlWriter.CDATA,String.valueOf(uniquenessChannel.getCode()));
    }
    */
    hd.startElement("","",EssencesXMLConstants.ESSENCE_TAG,attrs);
    hd.endElement("","",EssencesXMLConstants.ESSENCE_TAG);
  }
}
