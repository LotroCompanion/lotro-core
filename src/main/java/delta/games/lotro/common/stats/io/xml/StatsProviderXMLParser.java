package delta.games.lotro.common.stats.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for stats providers stored in XML.
 * @author DAM
 */
public class StatsProviderXMLParser
{
  /**
   * Build a stat provider from an XML tag.
   * @param root Root XML tag.
   * @return A stat provider.
   */
  private static StatProvider parseStatProvider(Element root)
  {
    StatProvider ret=null;
    NamedNodeMap attrs=root.getAttributes();
    // Stat name
    String statName=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_NAME_ATTR,null);
    STAT stat=STAT.getByName(statName);

    // Constant stat provider?
    String valueStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_VALUE_ATTR,null);
    if (valueStr!=null)
    {
      float value=NumericTools.parseFloat(valueStr,0);
      ConstantStatProvider constantStatProvider=new ConstantStatProvider(stat,value);
      return constantStatProvider;
    }
    // Scalable stat provider?
    String progressionStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_SCALING_ATTR,null);
    if (progressionStr!=null)
    {
      int progressionId=NumericTools.parseInt(progressionStr,-1);
      Progression progression=null;
      return new ScalableStatProvider(stat,progression);
    }
    return null;
  }
}
