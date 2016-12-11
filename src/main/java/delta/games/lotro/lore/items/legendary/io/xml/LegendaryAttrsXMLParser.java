package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Parser for legendary attributes stored in XML.
 * @author DAM
 */
public class LegendaryAttrsXMLParser
{
  /**
   * Read legendary attributes for an item.
   * @param legendaryAttrs Data to write to.
   * @param itemElement Root XML tag.
   */
  public static void read(LegendaryAttrs legendaryAttrs, Element itemElement)
  {
    // Read relics
    RelicsManager relicsMgr=RelicsManager.getInstance();
    List<Element> relicTags=DOMParsingTools.getChildTagsByName(itemElement,LegendaryAttrsXMLConstants.RELIC_TAG);
    for(Element relicTag : relicTags)
    {
      NamedNodeMap attrs=relicTag.getAttributes();
      String typeStr=DOMParsingTools.getStringAttribute(attrs,LegendaryAttrsXMLConstants.RELIC_TYPE_ATTR,null);
      if (typeStr!=null)
      {
        RelicType type=RelicType.valueOf(typeStr);
        Relic relic=null;
        String name=DOMParsingTools.getStringAttribute(attrs,LegendaryAttrsXMLConstants.RELIC_NAME_ATTR,null);
        if (name!=null)
        {
          relic=relicsMgr.getByName(name);
        }
        if (type==RelicType.SETTING) legendaryAttrs.setSetting(relic);
        if (type==RelicType.RUNE) legendaryAttrs.setRune(relic);
        if (type==RelicType.GEM) legendaryAttrs.setGem(relic);
        if (type==RelicType.CRAFTED_RELIC) legendaryAttrs.setCraftedRelic(relic);
      }
    }
  }
}
