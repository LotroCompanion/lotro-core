package delta.games.lotro.character.stats.base.io.xml;

import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Read a basic stats set from XML documents.
 * @author DAM
 */
public class BasicStatsSetXMLParser
{
  /**
   * Load a set of stats from a XML tag.
   * @param root Root tag.
   * @return A set of stats.
   */
  public static BasicStatsSet parseStats(Element root)
  {
    BasicStatsSet ret=new BasicStatsSet();
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,BasicStatsSetXMLConstants.STAT_TAG,false);
    for(Element statTag : statTags)
    {
      String statName=DOMParsingTools.getStringAttribute(statTag.getAttributes(),BasicStatsSetXMLConstants.STAT_NAME_ATTR,"");
      String statValue=DOMParsingTools.getStringAttribute(statTag.getAttributes(),BasicStatsSetXMLConstants.STAT_VALUE_ATTR,"");
      StatDescription stat=StatsRegistry.getInstance().getByKey(statName);
      FixedDecimalsInteger value=FixedDecimalsInteger.fromString(statValue);
      ret.setStat(stat,value);
    }
    return ret;
  }
}
