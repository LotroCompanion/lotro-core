package delta.games.lotro.lore.emotes.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Writes LOTRO emotes to XML files.
 * @author DAM
 */
public class EmoteXMLWriter
{
  /**
   * Write a file with emotes.
   * @param toFile Output file.
   * @param emotes Emotes to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeEmotesFile(File toFile, List<EmoteDescription> emotes)
  {
    EmoteXMLWriter writer=new EmoteXMLWriter();
    Collections.sort(emotes,new IdentifiableComparator<EmoteDescription>());
    boolean ok=writer.writeEmotes(toFile,emotes,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write emotes to a XML file.
   * @param outFile Output file.
   * @param emotes Emotes to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeEmotes(File outFile, final List<EmoteDescription> emotes, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeEmotes(hd,emotes);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeEmotes(TransformerHandler hd, List<EmoteDescription> emotes) throws SAXException
  {
    hd.startElement("","",EmoteXMLConstants.EMOTES_TAG,new AttributesImpl());
    for(EmoteDescription emote : emotes)
    {
      writeEmote(hd,emote);
    }
    hd.endElement("","",EmoteXMLConstants.EMOTES_TAG);
  }

  private void writeEmote(TransformerHandler hd, EmoteDescription emote) throws SAXException
  {
    AttributesImpl emoteAttrs=new AttributesImpl();

    // In-game identifier
    int id=emote.getIdentifier();
    if (id!=0)
    {
      emoteAttrs.addAttribute("","",EmoteXMLConstants.EMOTE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Command
    String command=emote.getCommand();
    if (command.length()>0)
    {
      emoteAttrs.addAttribute("","",EmoteXMLConstants.EMOTE_COMMAND_ATTR,XmlWriter.CDATA,command);
    }
    // Icon identifier
    int iconId=emote.getIconId();
    if (iconId!=0)
    {
      emoteAttrs.addAttribute("","",EmoteXMLConstants.EMOTE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    }
    // Auto
    boolean auto=emote.isAuto();
    if (auto)
    {
      emoteAttrs.addAttribute("","",EmoteXMLConstants.EMOTE_AUTO_ATTR,XmlWriter.CDATA,String.valueOf(auto));
    }
    // Description
    String description=emote.getDescription();
    if (description!=null)
    {
      emoteAttrs.addAttribute("","",EmoteXMLConstants.EMOTE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",EmoteXMLConstants.EMOTE_TAG,emoteAttrs);
    hd.endElement("","",EmoteXMLConstants.EMOTE_TAG);
  }
}
