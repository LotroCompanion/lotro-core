package delta.games.lotro.lore.collections.birds.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.collections.birds.BirdDescription;

/**
 * Writes birds to XML files.
 * @author DAM
 */
public class BirdsXMLWriter
{
  /**
   * Write the birds to a XML file.
   * @param toFile File to write to.
   * @param birds Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<BirdDescription> birds)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",BirdsXMLConstants.BIRDS,new AttributesImpl());
        for(BirdDescription bird : birds)
        {
          writeBird(hd,bird);
        }
        hd.endElement("","",BirdsXMLConstants.BIRDS);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeBird(TransformerHandler hd, BirdDescription bird) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=bird.getIdentifier();
    attrs.addAttribute("","",BirdsXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=bird.getName();
    attrs.addAttribute("","",BirdsXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Call sound ID
    int callSoundID=bird.getCallSoundID();
    if (callSoundID!=0)
    {
      attrs.addAttribute("","",BirdsXMLConstants.CALL_SOUND_ID_ATTR,XmlWriter.CDATA,String.valueOf(callSoundID));
    }
    // Type code
    int typeCode=bird.getTypeCode();
    attrs.addAttribute("","",BirdsXMLConstants.TYPE_CODE_ATTR,XmlWriter.CDATA,String.valueOf(typeCode));
    // Elvish name
    String elvishName=bird.getElvishName();
    attrs.addAttribute("","",BirdsXMLConstants.ELVISH_NAME_ATTR,XmlWriter.CDATA,elvishName);
    // Icon ID
    int iconID=bird.getIconID();
    attrs.addAttribute("","",BirdsXMLConstants.ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconID));
    // Large icon ID
    int largeIconID=bird.getLargeIconID();
    attrs.addAttribute("","",BirdsXMLConstants.LARGE_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(largeIconID));
    hd.startElement("","",BirdsXMLConstants.BIRD_TAG,attrs);
    hd.endElement("","",BirdsXMLConstants.BIRD_TAG);
  }
}
