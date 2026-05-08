package delta.games.lotro.character.status.portraitFrames.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.portraitFrames.PortraitFramesStatus;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * Writes a portrait frames status to an XML file.
 * @author DAM
 */
public class PortraitFramesStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final PortraitFramesStatus status, String encoding)
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
   * @param status Status to write.
   * @throws SAXException If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, PortraitFramesStatus status) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",PortraitFramesStatusXMLConstants.PORTRAIT_FRAMES_STATUS_TAG,attrs);

    // Current
    PortraitFrameDescription current=status.getCurrentPortraitFrame();
    if (current!=null)
    {
      writePortraitFrame(hd,PortraitFramesStatusXMLConstants.CURRENT_TAG,current);
    }
    // Unlocked
    List<PortraitFrameDescription> unlockedPortraitFrames=status.getUnlockedPortraitFrames();
    for(PortraitFrameDescription unlockedPortraitFrame : unlockedPortraitFrames)
    {
      writePortraitFrame(hd,PortraitFramesStatusXMLConstants.UNLOCKED_TAG,unlockedPortraitFrame);
    }
    hd.endElement("","",PortraitFramesStatusXMLConstants.PORTRAIT_FRAMES_STATUS_TAG);
  }

  private void writePortraitFrame(TransformerHandler hd, String tag, PortraitFrameDescription portraitFrame) throws SAXException
  {
    AttributesImpl statusAttrs=new AttributesImpl();
    // ID
    int id=portraitFrame.getIdentifier();
    statusAttrs.addAttribute("","",PortraitFramesStatusXMLConstants.PORTRAIT_FRAME_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=portraitFrame.getName();
    statusAttrs.addAttribute("","",PortraitFramesStatusXMLConstants.PORTRAIT_FRAME_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",tag,statusAttrs);
    hd.endElement("","",tag);
  }
}
