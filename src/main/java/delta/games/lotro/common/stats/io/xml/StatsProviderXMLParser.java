package delta.games.lotro.common.stats.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
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
  public static StatProvider parseStatProvider(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Stat name
    String statName=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_NAME_ATTR,null);
    STAT stat=STAT.getByName(statName);

    // Constant stat provider?
    String constantStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_CONSTANT_ATTR,null);
    if (constantStr!=null)
    {
      float value=NumericTools.parseFloat(constantStr,0);
      ConstantStatProvider constantStatProvider=new ConstantStatProvider(stat,value);
      return constantStatProvider;
    }
    // Scalable stat provider?
    String progressionStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_SCALING_ATTR,null);
    if (progressionStr!=null)
    {
      int progressionId=NumericTools.parseInt(progressionStr,-1);
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionId);
      return new ScalableStatProvider(stat,progression);
    }
    // Tiered scalable stat provider?
    String tieredProgressionStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_TIERED_SCALING_ATTR,null);
    if (tieredProgressionStr!=null)
    {
      String[] progressionIdStrs=tieredProgressionStr.split(";");
      int nbTiers=progressionIdStrs.length;
      TieredScalableStatProvider provider=new TieredScalableStatProvider(stat,nbTiers);
      int tier=1;
      for(String progressionIdStr : progressionIdStrs)
      {
        int progressionId=NumericTools.parseInt(progressionIdStr,-1);
        Progression progression=ProgressionsManager.getInstance().getProgression(progressionId);
        provider.setProgression(tier,progression);
        tier++;
      }
      return provider;
    }
    return null;
  }
}
