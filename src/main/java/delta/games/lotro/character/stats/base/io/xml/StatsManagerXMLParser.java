package delta.games.lotro.character.stats.base.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.CustomStatsMergeMode;
import delta.games.lotro.common.stats.StatsManager;

/**
 * Read a stats manager from XML documents.
 * @author DAM
 */
public class StatsManagerXMLParser
{
  /**
   * Load a stats manager from a XML tag.
   * @param root Root tag.
   * @param statsManager Storage to read data.
   */
  public static void parseStats(Element root, StatsManager statsManager)
  {
    NamedNodeMap attrs=root.getAttributes();
    String modeStr=DOMParsingTools.getStringAttribute(attrs,StatsManagerXMLConstants.STATS_MODE_ATTR,null);
    if (modeStr!=null)
    {
      CustomStatsMergeMode mode=CustomStatsMergeMode.valueOf(modeStr);
      statsManager.setMode(mode);
    }

    // Default stats
    Element defaultStatsTag=DOMParsingTools.getChildTagByName(root,StatsManagerXMLConstants.DEFAULT_STATS_TAG);
    if (defaultStatsTag!=null)
    {
      BasicStatsSet defaultStats=BasicStatsSetXMLParser.parseStats(defaultStatsTag);
      statsManager.getDefault().setStats(defaultStats);
    }
    // Custom stats
    Element customStatsTag=DOMParsingTools.getChildTagByName(root,StatsManagerXMLConstants.CUSTOM_STATS_TAG);
    if (customStatsTag!=null)
    {
      BasicStatsSet customStats=BasicStatsSetXMLParser.parseStats(customStatsTag);
      statsManager.getCustom().setStats(customStats);
    }
    // Result stats
    Element resultStatsTag=DOMParsingTools.getChildTagByName(root,StatsManagerXMLConstants.RESULT_STATS_TAG);
    if (resultStatsTag!=null)
    {
      BasicStatsSet resultStats=BasicStatsSetXMLParser.parseStats(resultStatsTag);
      statsManager.getResult().setStats(resultStats);
    }

    // Handle old storage
    if (modeStr==null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(root);
      statsManager.getCustom().setStats(stats);
      statsManager.getResult().setStats(stats);
      statsManager.setMode(CustomStatsMergeMode.SET);
    }
  }
}
