package delta.games.lotro.lore.items.legendary2.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary2.EnhancementRune;

/**
 * Parser for enhancement runes stored in XML.
 * @author DAM
 */
public class EnhancementRunesXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EnhancementRunesXMLParser.class);

  /**
   * Parse enhancement runes from an XML file.
   * @param source Source file.
   * @return List of parsed enhancement runes.
   */
  public static List<EnhancementRune> parseEnhancementRunesFile(File source)
  {
    List<EnhancementRune> ret=new ArrayList<EnhancementRune>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> runeTags=DOMParsingTools.getChildTags(root);
      for(Element runeTag : runeTags)
      {
        EnhancementRune enhancementRune=parseEnhancementRune(runeTag);
        if (enhancementRune!=null)
        {
          ret.add(enhancementRune);
        }
      }
    }
    return ret;
  }

  private static EnhancementRune parseEnhancementRune(Element runeTag)
  {
    NamedNodeMap attrs=runeTag.getAttributes();
    // Item Id
    int itemId=DOMParsingTools.getIntAttribute(attrs,EnhancementRunesXMLConstants.ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemId);
    if (item==null)
    {
      LOGGER.warn("Unknown item for enhancement rune: "+itemId);
      return null;
    }
    // Min item level
    int minItemLevel=DOMParsingTools.getIntAttribute(attrs,EnhancementRunesXMLConstants.MIN_ITEM_LEVEL_ATTR,0);
    // Max item level
    int maxItemLevel=DOMParsingTools.getIntAttribute(attrs,EnhancementRunesXMLConstants.MAX_ITEM_LEVEL_ATTR,0);
    // Increment
    int increment=DOMParsingTools.getIntAttribute(attrs,EnhancementRunesXMLConstants.LEVEL_INCREMENT_ATTR,1);
    EnhancementRune ret=new EnhancementRune(item,minItemLevel,maxItemLevel,increment);
    return ret;
  }
}
