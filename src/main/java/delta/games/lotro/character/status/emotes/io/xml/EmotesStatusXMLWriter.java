package delta.games.lotro.character.status.emotes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.emotes.EmoteStatus;
import delta.games.lotro.character.status.emotes.EmotesStatusManager;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Writes a emotes status to an XML file.
 * @author DAM
 */
public class EmotesStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final EmotesStatusManager status, String encoding)
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
  private void writeStatus(TransformerHandler hd, EmotesStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",EmotesStatusXMLConstants.EMOTES_STATUS_TAG,attrs);

    List<EmoteStatus> emoteStatuses=statusMgr.getAll();
    for(EmoteStatus emoteStatus : emoteStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      EmoteDescription emote=emoteStatus.getEmote();
      // ID
      int emoteID=emote.getIdentifier();
      statusAttrs.addAttribute("","",EmotesStatusXMLConstants.EMOTE_ID_ATTR,XmlWriter.CDATA,String.valueOf(emoteID));
      // Name
      String name=emote.getName();
      statusAttrs.addAttribute("","",EmotesStatusXMLConstants.EMOTE_NAME_ATTR,XmlWriter.CDATA,name);
      // Available
      boolean available=emoteStatus.isAvailable();
      if (available)
      {
        statusAttrs.addAttribute("","",EmotesStatusXMLConstants.EMOTE_AVAILABLE_ATTR,XmlWriter.CDATA,String.valueOf(available));
      }
      hd.startElement("","",EmotesStatusXMLConstants.EMOTE,statusAttrs);
      hd.endElement("","",EmotesStatusXMLConstants.EMOTE);
    }
    hd.endElement("","",EmotesStatusXMLConstants.EMOTES_STATUS_TAG);
  }
}
