package delta.games.lotro.common.stats.io.xml;

import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for stats providers stored in XML.
 * @author DAM
 */
public class StatsProviderXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(StatsProviderXMLParser.class);

  /**
   * Build a stats provider from an XML tag.
   * @param root Root XML tag.
   * @return A stats provider.
   */
  public static StatsProvider parseStatsProvider(Element root)
  {
    StatsProvider statsProvider=new StatsProvider();
    parseStatsProvider(root,statsProvider);
    return statsProvider;
  }

  /**
   * Build a stats provider from an XML tag.
   * @param root Root XML tag.
   * @param statsProvider Storage.
   */
  public static void parseStatsProvider(Element root, StatsProvider statsProvider)
  {
    // Stats
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,StatsProviderXMLConstants.STAT_TAG);
    int nbStatsTags=statTags.size();
    if (nbStatsTags>0)
    {
      for(Element statTag : statTags)
      {
        StatProvider statProvider=StatsProviderXMLParser.parseStatProvider(statTag);
        statsProvider.addStatProvider(statProvider);
      }
    }
    // Special effects
    List<Element> specialEffectsTags=DOMParsingTools.getChildTagsByName(root,StatsProviderXMLConstants.SPECIAL_EFFECT_TAG);
    int nbSpecialEffectTags=specialEffectsTags.size();
    if (nbSpecialEffectTags>0)
    {
      for(Element specialEffectsTag : specialEffectsTags)
      {
        String label=DOMParsingTools.getStringAttribute(specialEffectsTag.getAttributes(),StatsProviderXMLConstants.SPECIAL_EFFECT_LABEL_ATTR,null);
        if (label!=null)
        {
          SpecialEffect specialEffect=new SpecialEffect(label);
          statsProvider.addSpecialEffect(specialEffect);
        }
      }
    }
  }

  /**
   * Build a stat provider from an XML tag.
   * @param root Root XML tag.
   * @return A stat provider.
   */
  private static StatProvider parseStatProvider(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Stat name
    String statName=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_NAME_ATTR,null);
    StatDescription stat=StatsRegistry.getInstance().getByKey(statName);
    if (stat==null)
    {
      return null;
    }

    StatProvider provider=parseStatProvider(attrs,stat);
    if (provider!=null)
    {
      // Stat operator
      String operatorStr=DOMParsingTools.getStringAttribute(attrs,StatsProviderXMLConstants.STAT_OPERATOR_ATTR,null);
      StatOperator operator=StatOperator.getByName(operatorStr);
      if (operator==null)
      {
        operator=StatOperator.ADD;
      }
      provider.setOperator(operator);

      // Description override
      String descriptionOverride=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR,null);
      provider.setDescriptionOverride(descriptionOverride);
    }
    return provider;
  }

  private static StatProvider parseStatProvider(NamedNodeMap attrs, StatDescription stat)
  {
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
      if (progression==null)
      {
        LOGGER.warn("Progression not found: "+progressionId);
      }
      ScalableStatProvider provider=new ScalableStatProvider(stat,progression);
      return provider;
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
