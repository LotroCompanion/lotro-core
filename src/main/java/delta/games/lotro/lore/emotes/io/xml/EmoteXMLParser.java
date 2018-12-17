package delta.games.lotro.lore.emotes.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Parser for emote descriptions stored in XML.
 * @author DAM
 */
public class EmoteXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed titles.
   */
  public List<EmoteDescription> parseXML(File source)
  {
    List<EmoteDescription> ret=new ArrayList<EmoteDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> emoteTags=DOMParsingTools.getChildTagsByName(root,EmoteXMLConstants.EMOTE_TAG);
      for(Element emoteTag : emoteTags)
      {
        EmoteDescription emote=parseEmote(emoteTag);
        ret.add(emote);
      }
    }
    return ret;
  }

  private EmoteDescription parseEmote(Element root)
  {
    EmoteDescription title=new EmoteDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EmoteXMLConstants.EMOTE_ID_ATTR,0);
    title.setIdentifier(id);
    // Command
    String command=DOMParsingTools.getStringAttribute(attrs,EmoteXMLConstants.EMOTE_COMMAND_ATTR,null);
    title.setCommand(command);
    // Icon
    int iconId=DOMParsingTools.getIntAttribute(attrs,EmoteXMLConstants.EMOTE_ICON_ATTR,0);
    title.setIconId(iconId);
    // Auto
    boolean auto=DOMParsingTools.getBooleanAttribute(attrs,EmoteXMLConstants.EMOTE_AUTO_ATTR,false);
    title.setAuto(auto);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,EmoteXMLConstants.EMOTE_DESCRIPTION_ATTR,null);
    title.setDescription(description);
    return title;
  }
}
