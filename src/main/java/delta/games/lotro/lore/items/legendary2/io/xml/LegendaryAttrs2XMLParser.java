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
import delta.games.lotro.lore.items.legendary2.LegendaryAttrs2;
import delta.games.lotro.lore.items.legendary2.SocketEntry;
import delta.games.lotro.lore.items.legendary2.SocketsSetup;

/**
 * Parser for legendary attributes (reloaded) stored in XML.
 * @author DAM
 */
public class LegendaryAttrs2XMLParser
{
  /**
   * Parse a legendary attributes (reloaded) from an XML file.
   * @param source Source file.
   * @return List of parsed data.
   */
  public static List<LegendaryAttrs2> parseLegendaryAttributesFile(File source)
  {
    List<LegendaryAttrs2> legendaryAttributes=new ArrayList<LegendaryAttrs2>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> legendaryAttrsTags=DOMParsingTools.getChildTags(root);
      for(Element legendaryAttrsTag : legendaryAttrsTags)
      {
        LegendaryAttrs2 attributes=parseLegendaryAttributes(legendaryAttrsTag);
        legendaryAttributes.add(attributes);
      }
    }
    return legendaryAttributes;
  }

  private static LegendaryAttrs2 parseLegendaryAttributes(Element root)
  {
    LegendaryAttrs2 ret=new LegendaryAttrs2();
    int itemId=DOMParsingTools.getIntAttribute(root.getAttributes(),LegendaryAttrs2XMLConstants.ITEM_ID_ATTR,0);
    SocketsSetup setup=new SocketsSetup(itemId);
    LotroEnum<SocketType> socketTypesMgr=LotroEnumsRegistry.getInstance().get(SocketType.class);
    List<Element> socketEntryTags=DOMParsingTools.getChildTagsByName(root,LegendaryAttrs2XMLConstants.SOCKET_SETUP_TAG);
    for(Element socketEntryTag : socketEntryTags)
    {
      NamedNodeMap entryAttrs=socketEntryTag.getAttributes();
      int typeCode=DOMParsingTools.getIntAttribute(entryAttrs,LegendaryAttrs2XMLConstants.SOCKET_TYPE_ATTR,0);
      SocketType type=socketTypesMgr.getEntry(typeCode);
      int unlockLevel=DOMParsingTools.getIntAttribute(entryAttrs,LegendaryAttrs2XMLConstants.SOCKET_UNLOCK_LEVEL_ATTR,0);
      SocketEntry entry=new SocketEntry(type,unlockLevel);
      setup.addSocket(entry);
    }
    ret.setSockets(setup);
    return ret;
  }
}
