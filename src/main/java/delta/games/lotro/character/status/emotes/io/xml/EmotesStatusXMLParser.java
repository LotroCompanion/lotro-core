package delta.games.lotro.character.status.emotes.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.emotes.EmoteStatus;
import delta.games.lotro.character.status.emotes.EmotesStatusManager;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.emotes.EmotesManager;

/**
 * Parser for the emotes status stored in XML.
 * @author DAM
 */
public class EmotesStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EmotesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public EmotesStatusManager parseXML(File source)
  {
    EmotesStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private EmotesStatusManager parseStatus(Element root)
  {
    EmotesStatusManager status=new EmotesStatusManager();
    // Status of emotes
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,EmotesStatusXMLConstants.EMOTE,false);
    for(Element statusTag : statusTags)
    {
      parseEmoteStatus(status,statusTag);
    }
    return status;
  }

  private void parseEmoteStatus(EmotesStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,EmotesStatusXMLConstants.EMOTE_ID_ATTR,0);
    // Create status
    EmotesManager mgr=EmotesManager.getInstance();
    EmoteDescription emote=mgr.getEmote(id);
    if (emote==null)
    {
      // Unknown emote!
      LOGGER.warn("Unknown emote: {}",Integer.valueOf(id));
      return;
    }
    EmoteStatus newStatus=status.get(emote,true);
    // Available
    boolean available=DOMParsingTools.getBooleanAttribute(attrs,EmotesStatusXMLConstants.EMOTE_AVAILABLE_ATTR,false);
    newStatus.setAvailable(available);
  }
}
