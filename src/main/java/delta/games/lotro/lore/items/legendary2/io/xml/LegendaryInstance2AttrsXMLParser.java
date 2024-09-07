package delta.games.lotro.lore.items.legendary2.io.xml;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.legendary2.LegendaryInstanceAttrs2;
import delta.games.lotro.lore.items.legendary2.SocketEntryInstance;
import delta.games.lotro.lore.items.legendary2.SocketsSetupInstance;
import delta.games.lotro.lore.items.legendary2.TraceriesManager;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Parser for legendary instance attributes (reloaded) stored in XML.
 * @author DAM
 */
public class LegendaryInstance2AttrsXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(LegendaryInstance2AttrsXMLParser.class);

  /**
   * Read legendary instance attributes for an item.
   * @param legendaryAttrs Data to write to.
   * @param itemElement Root XML tag.
   */
  public static void read(LegendaryInstanceAttrs2 legendaryAttrs, Element itemElement)
  {
    Element legendaryElement=DOMParsingTools.getChildTagByName(itemElement,LegendaryInstance2AttrsXMLConstants.LEGENDARY_TAG);
    if (legendaryElement!=null)
    {
      // Name
      String name=DOMParsingTools.getStringAttribute(legendaryElement.getAttributes(),LegendaryInstance2AttrsXMLConstants.LEGENDARY_NAME_ATTR,null);
      legendaryAttrs.setLegendaryName(name);
      // Traceries
      SocketsSetupInstance sockets=legendaryAttrs.getSocketsSetup();
      List<Element> socketTags=DOMParsingTools.getChildTagsByName(legendaryElement,LegendaryInstance2AttrsXMLConstants.SOCKET_TAG);
      for(Element socketTag : socketTags)
      {
        NamedNodeMap socketAttrs=socketTag.getAttributes();
        int index=DOMParsingTools.getIntAttribute(socketAttrs,LegendaryInstance2AttrsXMLConstants.SOCKET_INDEX_ATTR,-1);
        SocketEntryInstance entry=sockets.getEntry(index);
        if (entry==null)
        {
          LOGGER.warn("No entry at index: "+index);
          continue;
        }
        int traceryID=DOMParsingTools.getIntAttribute(socketAttrs,LegendaryInstance2AttrsXMLConstants.SOCKET_TRACERY_ID_ATTR,0);
        Tracery tracery=TraceriesManager.getInstance().getTracery(traceryID);
        int itemLevel=DOMParsingTools.getIntAttribute(socketAttrs,LegendaryInstance2AttrsXMLConstants.SOCKET_ITEM_LEVEL_ATTR,1);
        if (tracery!=null)
        {
          entry.setTracery(tracery);
          entry.setItemLevel(itemLevel);
        }
        else
        {
          LOGGER.warn("Tracery not found: "+traceryID);
        }
      }
    }
  }
}
