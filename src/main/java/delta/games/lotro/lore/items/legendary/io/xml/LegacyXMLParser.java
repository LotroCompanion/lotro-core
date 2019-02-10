package delta.games.lotro.lore.items.legendary.io.xml;

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
import delta.games.lotro.lore.items.legendary.LegacyType;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;

/**
 * Parser for legacies descriptions stored in XML.
 * @author DAM
 */
public class LegacyXMLParser
{
  /**
   * Parse a legacies XML file.
   * @param source Source file.
   * @return List of parsed legacies.
   */
  public static List<ImbuedLegacy> parseLegaciesFile(File source)
  {
    List<ImbuedLegacy> legacies=new ArrayList<ImbuedLegacy>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> legacyTags=DOMParsingTools.getChildTagsByName(root,LegacyXMLConstants.LEGACY_TAG);
      for(Element legacyTag : legacyTags)
      {
        ImbuedLegacy legacy=parseLegacy(legacyTag);
        legacies.add(legacy);
      }
    }
    return legacies;
  }

  /**
   * Build a legacy from an XML tag.
   * @param root Root XML tag.
   * @return A legacy.
   */
  private static ImbuedLegacy parseLegacy(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    ImbuedLegacy legacy=new ImbuedLegacy();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_IDENTIFIER_ATTR,0);
    legacy.setIdentifier(id);
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(attrs,LegacyXMLConstants.LEGACY_TYPE_ATTR,null);
    if (typeStr!=null)
    {
      LegacyType type=LegacyType.valueOf(typeStr);
      legacy.setType(type);
    }
    // Maximum initial level
    int maximumInitialLevel=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_MAX_INITIAL_LEVEL_ATTR,1);
    legacy.setMaxInitialLevel(maximumInitialLevel);
    // Maximum level
    int maxLevel=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_MAX_LEVEL_ATTR,1);
    legacy.setMaxLevel(maxLevel);

    // Stats
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,StatsProviderXMLConstants.STAT_TAG);
    int nbStatsTags=statTags.size();
    if (nbStatsTags>0)
    {
      StatsProvider statsProvider=new StatsProvider();
      for(Element statTag : statTags)
      {
        StatProvider statProvider=StatsProviderXMLParser.parseStatProvider(statTag);
        statsProvider.addStatProvider(statProvider);
      }
      legacy.setStatsProvider(statsProvider);
    }
    return legacy;
  }
}
