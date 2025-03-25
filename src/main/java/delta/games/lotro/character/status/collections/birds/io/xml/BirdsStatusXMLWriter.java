package delta.games.lotro.character.status.collections.birds.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.collections.birds.BirdsStatusManager;
import delta.games.lotro.lore.collections.birds.BirdDescription;
import delta.games.lotro.lore.collections.birds.BirdsManager;

/**
 * Writes a birds status to an XML file.
 * @author DAM
 */
public class BirdsStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final BirdsStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws SAXException If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, BirdsStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",BirdsStatusXMLConstants.BIRDS_STATUS_TAG,attrs);

    List<BirdDescription> birds=BirdsManager.getInstance().getAll();
    for(BirdDescription bird : birds)
    {
      int birdID=bird.getIdentifier();
      boolean known=statusMgr.isKnown(birdID);
      if (!known)
      {
        continue;
      }
      AttributesImpl statusAttrs=new AttributesImpl();
      // ID
      statusAttrs.addAttribute("","",BirdsStatusXMLConstants.BIRD_ID_ATTR,XmlWriter.CDATA,String.valueOf(birdID));
      // Name
      String name=bird.getName();
      statusAttrs.addAttribute("","",BirdsStatusXMLConstants.BIRD_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",BirdsStatusXMLConstants.BIRD,statusAttrs);
      hd.endElement("","",BirdsStatusXMLConstants.BIRD);
    }
    hd.endElement("","",BirdsStatusXMLConstants.BIRDS_STATUS_TAG);
  }
}
