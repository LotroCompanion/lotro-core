package delta.games.lotro.lore.consumables.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLConstants;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.consumables.Consumable;

/**
 * Parser for consumables stored in XML.
 * @author DAM
 */
public class ConsumableXMLParser
{
  /**
   * Parse consumables from an XML file.
   * @param source Source file.
   * @return List of parsed consumables.
   */
  public static List<Consumable> parseConsumablesFile(File source)
  {
    List<Consumable> consumables=new ArrayList<Consumable>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> consumableTags=DOMParsingTools.getChildTagsByName(root,ConsumableXMLConstants.CONSUMABLE_TAG);
      for(Element consumableTag : consumableTags)
      {
        Consumable consumable=parseConsumable(consumableTag);
        consumables.add(consumable);
      }
    }
    return consumables;
  }

  /**
   * Build a consumable from an XML tag.
   * @param root Root XML tag.
   * @return A consumable.
   */
  private static Consumable parseConsumable(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ConsumableXMLConstants.CONSUMABLE_IDENTIFIER_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,ConsumableXMLConstants.CONSUMABLE_NAME_ATTR,null);
    // Icon ID
    String iconId=DOMParsingTools.getStringAttribute(attrs,ConsumableXMLConstants.CONSUMABLE_ICON_ID_ATTR,null);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,ConsumableXMLConstants.CONSUMABLE_CATEGORY_ATTR,null);

    Consumable consumable=new Consumable(id,name,iconId,category);
    // Stats
    StatsProvider statsProvider=consumable.getProvider();
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,StatsProviderXMLConstants.STAT_TAG);
    for(Element statTag : statTags)
    {
      StatProvider statProvider=StatsProviderXMLParser.parseStatProvider(statTag);
      statsProvider.addStatProvider(statProvider);
    }
    return consumable;
  }
}
