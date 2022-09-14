package delta.games.lotro.character.status.hobbies.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.hobbies.HobbiesStatusManager;
import delta.games.lotro.character.status.hobbies.HobbyStatus;
import delta.games.lotro.lore.hobbies.HobbyDescription;

/**
 * Writes a hobbies status to an XML file.
 * @author DAM
 */
public class HobbiesStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final HobbiesStatusManager status, String encoding)
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
  private void writeStatus(TransformerHandler hd, HobbiesStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",HobbiesStatusXMLConstants.HOBBIES_STATUS_TAG,attrs);

    List<HobbyStatus> hobbiesStatuses=statusMgr.getAll();
    for(HobbyStatus hobbyStatus : hobbiesStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      HobbyDescription hobby=hobbyStatus.getHobby();
      // ID
      int hobbyID=hobby.getIdentifier();
      statusAttrs.addAttribute("","",HobbiesStatusXMLConstants.HOBBY_ID_ATTR,XmlWriter.CDATA,String.valueOf(hobbyID));
      // Name
      String name=hobby.getName();
      statusAttrs.addAttribute("","",HobbiesStatusXMLConstants.HOBBY_NAME_ATTR,XmlWriter.CDATA,name);
      // Value
      int value=hobbyStatus.getValue();
      statusAttrs.addAttribute("","",HobbiesStatusXMLConstants.HOBBY_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(value));
      hd.startElement("","",HobbiesStatusXMLConstants.HOBBY,statusAttrs);
      hd.endElement("","",HobbiesStatusXMLConstants.HOBBY);
    }
    hd.endElement("","",HobbiesStatusXMLConstants.HOBBIES_STATUS_TAG);
  }
}
