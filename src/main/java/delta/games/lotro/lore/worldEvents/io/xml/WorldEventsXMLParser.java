package delta.games.lotro.lore.worldEvents.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.worldEvents.WorldEvent;

/**
 * Parser for world events stored in XML.
 * @author DAM
 */
public class WorldEventsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded world events or <code>null</code>.
   */
  public List<WorldEvent> parseXML(File source)
  {
    List<WorldEvent> ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseWorldEvents(root);
    }
    return ret;
  }

  private static List<WorldEvent> parseWorldEvents(Element mainTag)
  {
    List<WorldEvent> ret=new ArrayList<WorldEvent>();
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(mainTag,WorldEventsXMLConstants.WORLD_EVENT_TAG,false);
    for(Element elementTag : elementTags)
    {
      WorldEvent worldEvent=parseWorldEvent(elementTag);
      if (worldEvent!=null)
      {
        ret.add(worldEvent);
      }
    }
    return ret;
  }

  /**
   * Read a world event from a tag.
   * @param elementTag Tag to read.
   * @return the loaded world event or <code>null</code>.
   */
  private static WorldEvent parseWorldEvent(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Identifier
    int identifier=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_ID_ATTR,0);
    // Property ID
    int propertyID=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_ID_ATTR,0);
    WorldEvent ret=new WorldEvent(identifier,propertyID);
    // Property name
    String propertyName=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_NAME_ATTR,"");
    ret.setPropertyName(propertyName);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_DESCRIPTION_ATTR,null);
    ret.setDescription(description);
    // Progress
    String progress=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROGRESS_ATTR,null);
    ret.setProgress(progress);
    return ret;
  }
}
