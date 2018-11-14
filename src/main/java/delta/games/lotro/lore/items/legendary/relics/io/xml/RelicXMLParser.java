package delta.games.lotro.lore.items.legendary.relics.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsCategory;

/**
 * Parser for relic descriptions stored in XML.
 * @author DAM
 */
public class RelicXMLParser
{
  /**
   * Parse a relics XML file.
   * @param source Source file.
   * @return List of parsed relics categories.
   */
  public List<RelicsCategory> parseRelicsFile(File source)
  {
    List<RelicsCategory> categories=new ArrayList<RelicsCategory>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> categoryTags=DOMParsingTools.getChildTagsByName(root,RelicXMLConstants.CATEGORY_TAG);
      for(Element categoryTag:categoryTags)
      {
        RelicsCategory category=parseCategory(categoryTag);
        categories.add(category);
      }
    }
    return categories;
  }

  /**
   * Build a relics category from an XML tag.
   * @param root Root XML tag.
   * @return A relics category.
   */
  private RelicsCategory parseCategory(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Name
    String categoryStr=DOMParsingTools.getStringAttribute(attrs,RelicXMLConstants.CATEGORY_NAME_ATTR,null);
    RelicsCategory category=new RelicsCategory(categoryStr);
    List<Element> relicTags=DOMParsingTools.getChildTagsByName(root,RelicXMLConstants.RELIC_TAG);
    for(Element relicTag:relicTags)
    {
      Relic relic=parseRelic(relicTag);
      relic.setCategory(categoryStr);
      category.addRelic(relic);
    }
    return category;
  }

  /**
   * Build a relic from an XML tag.
   * @param root Root XML tag.
   * @return A relic.
   */
  private Relic parseRelic(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,RelicXMLConstants.RELIC_ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,RelicXMLConstants.RELIC_NAME_ATTR,null);
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(attrs,RelicXMLConstants.RELIC_TYPE_ATTR,null);
    RelicType type=RelicType.valueOf(typeStr);
    // Item level
    int level=DOMParsingTools.getIntAttribute(attrs,RelicXMLConstants.RELIC_LEVEL_ATTR,-1);
    Integer relicLevel=(level!=-1)?Integer.valueOf(level):null;
    Relic relic=new Relic(id,name,type,relicLevel);
    // Icon filename
    String iconFilename=DOMParsingTools.getStringAttribute(attrs,RelicXMLConstants.RELIC_ICON_FILENAME_ATTR,null);
    relic.setIconFilename(iconFilename);
    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,BasicStatsSetXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      relic.getStats().addStats(stats);
    }
    return relic;
  }
}
