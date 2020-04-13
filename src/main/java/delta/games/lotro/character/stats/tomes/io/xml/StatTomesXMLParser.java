package delta.games.lotro.character.stats.tomes.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.character.stats.tomes.StatTome;
import delta.games.lotro.character.stats.tomes.StatTomesManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;

/**
 * Parser for stat tomes stored in XML.
 * @author DAM
 */
public class StatTomesXMLParser
{
  /**
   * Parse a stat tomes XML file.
   * @param source Source file.
   * @return the loaded data.
   */
  public static StatTomesManager parseStatTomesFile(File source)
  {
    StatTomesManager storage=new StatTomesManager();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> statTags=DOMParsingTools.getChildTagsByName(root,StatTomesXMLConstants.STAT_TAG,false);
      for(Element statTag : statTags)
      {
        // Stat key
        String key=DOMParsingTools.getStringAttribute(statTag.getAttributes(),StatTomesXMLConstants.STAT_IDENTIFIER_ATTR,"");
        StatDescription stat=StatsRegistry.getInstance().getByKey(key);
        List<Element> tomeTags=DOMParsingTools.getChildTagsByName(statTag,StatTomesXMLConstants.TOME_TAG);
        for(Element tomeTag : tomeTags)
        {
          NamedNodeMap tomeAttrs=tomeTag.getAttributes();
          // Rank
          int rank=DOMParsingTools.getIntAttribute(tomeAttrs,StatTomesXMLConstants.TOME_RANK_ATTR,0);
          // Trait identifier
          int traitId=DOMParsingTools.getIntAttribute(tomeAttrs,StatTomesXMLConstants.TOME_TRAIT_ID_ATTR,0);
          // Stats
          Element statsTag=DOMParsingTools.getChildTagByName(tomeTag,BasicStatsSetXMLConstants.STATS_TAG);
          BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
          StatTome tome=new StatTome(stat,rank,traitId,stats);
          storage.registerStatTome(tome);
        }
      }
    }
    return storage;
  }
}
