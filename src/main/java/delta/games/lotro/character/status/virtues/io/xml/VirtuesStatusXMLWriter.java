package delta.games.lotro.character.status.virtues.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.status.virtues.SingleVirtueStatus;
import delta.games.lotro.character.status.virtues.VirtuesStatus;
import delta.games.lotro.character.virtues.VirtueDescription;

/**
 * Writes virtues status to XML files.
 * @author DAM
 */
public class VirtuesStatusXMLWriter
{
  /**
   * Write virtues status to a XML file.
   * @param toFile File to write to.
   * @param status Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final VirtuesStatus status)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",VirtuesStatusXMLConstants.VIRTUES_TAG,new AttributesImpl());
        writeVirtuesStatus(hd,status);
        hd.endElement("","",VirtuesStatusXMLConstants.VIRTUES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeVirtuesStatus(TransformerHandler hd, VirtuesStatus virtuesStatus) throws SAXException
  {
    List<SingleVirtueStatus> statuses=virtuesStatus.getAll();
    for(SingleVirtueStatus status : statuses)
    {
      VirtueDescription virtue=status.getVirtue();
      AttributesImpl attrs=new AttributesImpl();
     // Identifier
      int id=virtue.getIdentifier();
      attrs.addAttribute("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=virtue.getName();
      attrs.addAttribute("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_NAME_ATTR,XmlWriter.CDATA,name);
      // XP
      int xp=status.getXp();
      attrs.addAttribute("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
      // Tier
      int tier=status.getTier();
      attrs.addAttribute("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));

      hd.startElement("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_TAG,attrs);
      hd.endElement("","",VirtuesStatusXMLConstants.VIRTUE_STATUS_TAG);
    }
  }
}
