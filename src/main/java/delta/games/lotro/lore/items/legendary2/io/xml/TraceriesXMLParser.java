package delta.games.lotro.lore.items.legendary2.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Parser for traceries stored in XML.
 * @author DAM
 */
public class TraceriesXMLParser
{
  /**
   * Parse traceries from an XML file.
   * @param source Source file.
   * @return List of parsed traceries.
   */
  public static List<Tracery> parseTraceriesFile(File source)
  {
    List<Tracery> ret=new ArrayList<Tracery>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> traceryTags=DOMParsingTools.getChildTags(root);
      for(Element traceryTag : traceryTags)
      {
        Tracery attributes=parseTracery(traceryTag);
        ret.add(attributes);
      }
    }
    return ret;
  }

  private static Tracery parseTracery(Element traceryTag)
  {
    NamedNodeMap attrs=traceryTag.getAttributes();
    // Item Id
    int itemId=DOMParsingTools.getIntAttribute(attrs,TraceriesXMLConstants.ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemId);
    if (item==null)
    {
      return null;
    }
    // Socket type
    LotroEnum<SocketType> socketTypesMgr=LotroEnumsRegistry.getInstance().get(SocketType.class);
    int typeCode=DOMParsingTools.getIntAttribute(attrs,TraceriesXMLConstants.SOCKET_TYPE_ATTR,0);
    SocketType socketType=socketTypesMgr.getEntry(typeCode);
    // Min item level
    int minItemLevel=DOMParsingTools.getIntAttribute(attrs,TraceriesXMLConstants.MIN_ITEM_LEVEL_ATTR,0);
    // Max item level
    int maxItemLevel=DOMParsingTools.getIntAttribute(attrs,TraceriesXMLConstants.MAX_ITEM_LEVEL_ATTR,0);
    // Increment
    int increment=DOMParsingTools.getIntAttribute(attrs,TraceriesXMLConstants.LEVEL_INCREMENT_ATTR,1);
    Tracery ret=new Tracery(item,socketType,minItemLevel,maxItemLevel,increment);
    return ret;
  }
}
