package delta.games.lotro.lore.items.legendary.titles.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryTitleXMLConstants;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

/**
 * Parser for legendary titles descriptions stored in XML.
 * @author DAM
 */
public class LegendaryTitleXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed legendary titles.
   */
  public static List<LegendaryTitle> parseXML(File source)
  {
    List<LegendaryTitle> ret=new ArrayList<LegendaryTitle>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> titleTags=DOMParsingTools.getChildTagsByName(root,LegendaryTitleXMLConstants.TITLE_TAG);
      for(Element titleTag : titleTags)
      {
        LegendaryTitle title=parseTitle(titleTag);
        ret.add(title);
      }
    }
    return ret;
  }

  private static LegendaryTitle parseTitle(Element root)
  {
    LegendaryTitle title=new LegendaryTitle();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_ID_ATTR,0);
    title.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,LegendaryTitleXMLConstants.TITLE_NAME_ATTR,"");
    title.setName(name);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,LegendaryTitleXMLConstants.TITLE_CATEGORY_ATTR,null);
    title.setCategory(category);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_TIER_ATTR,1);
    title.setTier(tier);
    // Damage type
    String damageTypeStr=DOMParsingTools.getStringAttribute(attrs,LegendaryTitleXMLConstants.TITLE_DAMAGE_TYPE_ATTR,null);
    DamageType damageType=DamageType.getDamageTypeByKey(damageTypeStr);
    title.setDamageType(damageType);
    // Slayer genus
    String slayerGenus=DOMParsingTools.getStringAttribute(attrs,LegendaryTitleXMLConstants.TITLE_SLAYER_ATTR,null);
    title.setSlayerGenusType(slayerGenus);
    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,LegendaryTitleXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      title.getStats().setStats(stats);
    }
    return title;
  }
}
