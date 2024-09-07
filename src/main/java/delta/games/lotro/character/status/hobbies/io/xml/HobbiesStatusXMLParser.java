package delta.games.lotro.character.status.hobbies.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.hobbies.HobbiesStatusManager;
import delta.games.lotro.character.status.hobbies.HobbyStatus;
import delta.games.lotro.lore.hobbies.HobbiesManager;
import delta.games.lotro.lore.hobbies.HobbyDescription;

/**
 * Parser for the hobbies status stored in XML.
 * @author DAM
 */
public class HobbiesStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(HobbiesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public HobbiesStatusManager parseXML(File source)
  {
    HobbiesStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private HobbiesStatusManager parseStatus(Element root)
  {
    HobbiesStatusManager status=new HobbiesStatusManager();
    // Status of hobbies
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,HobbiesStatusXMLConstants.HOBBY,false);
    for(Element statusTag : statusTags)
    {
      parseHobbyStatus(status,statusTag);
    }
    return status;
  }

  private void parseHobbyStatus(HobbiesStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,HobbiesStatusXMLConstants.HOBBY_ID_ATTR,0);
    // Create status
    HobbiesManager mgr=HobbiesManager.getInstance();
    HobbyDescription hobby=mgr.getHobby(id);
    if (hobby==null)
    {
      // Unknown hobby!
      LOGGER.warn("Unknown hobby: "+id);
      return;
    }
    HobbyStatus newStatus=status.get(hobby,true);
    // Value
    int value=DOMParsingTools.getIntAttribute(attrs,HobbiesStatusXMLConstants.HOBBY_VALUE_ATTR,0);
    newStatus.setValue(value);
  }
}
