package delta.games.lotro.lore.portraitFrames.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * Writes some portrait frames to an XML document.
 * @author DAM
 */
public class PortraitFramesXMLWriter
{
  /**
   * Write some portrait frames to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<PortraitFrameDescription> data, String encoding)
  {
    Collections.sort(data,new IdentifiableComparator<PortraitFrameDescription>());
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",PortraitFramesXMLConstants.PORTRAIT_FRAMES_TAG,attrs);
        for(PortraitFrameDescription element : data)
        {
          writePortraitFrame(hd,element);
        }
        hd.endElement("","",PortraitFramesXMLConstants.PORTRAIT_FRAMES_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private static void writePortraitFrame(TransformerHandler hd, PortraitFrameDescription portraitFrame) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // ID
    int id=portraitFrame.getIdentifier();
    attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=portraitFrame.getName();
    attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_NAME_ATTR,XmlWriter.CDATA,name);
    // Code
    int code=portraitFrame.getCode();
    attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Icon name
    String iconName=portraitFrame.getIconName();
    attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_ICON_ATTR,XmlWriter.CDATA,iconName);
    // Is for freeps
    boolean isForFreeps=portraitFrame.isForFreeps();
    if (!isForFreeps)
    {
      attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_FREEPS_ATTR,XmlWriter.CDATA,String.valueOf(isForFreeps));
    }
    // Is for creeps
    boolean isForCreeps=portraitFrame.isForCreeps();
    if (isForCreeps)
    {
      attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_CREEPS_ATTR,XmlWriter.CDATA,String.valueOf(isForCreeps));
    }
    // Is for PVP characters
    boolean isForPvpCharacters=portraitFrame.isForPvpCharacters();
    if (isForPvpCharacters)
    {
      attrs.addAttribute("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_PVP_CHARACTERS_ATTR,XmlWriter.CDATA,String.valueOf(isForPvpCharacters));
    }
    hd.startElement("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_TAG,attrs);
    hd.endElement("","",PortraitFramesXMLConstants.PORTRAIT_FRAME_TAG);
  }
}
