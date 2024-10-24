package delta.games.lotro.common.stats.io.xml;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for stats providers stored in XML.
 * @author DAM
 */
public class StatsProviderXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(StatsProviderXMLParser.class);

  /**
   * Build a stats provider from an XML tag.
   * @param root Root XML tag.
   * @param i18n Labels manager.
   * @return A stats provider.
   */
  public static StatsProvider parseStatsProvider(Element root, SingleLocaleLabelsManager i18n)
  {
    StatsProvider statsProvider=new StatsProvider();
    parseStatsProvider(root,statsProvider,i18n);
    return statsProvider;
  }

  /**
   * Build a stats provider from an XML tag.
   * @param root Root XML tag.
   * @param i18n Labels manager.
   * @param statsProvider Storage.
   */
  public static void parseStatsProvider(Element root, StatsProvider statsProvider, SingleLocaleLabelsManager i18n)
  {
    List<Element> childTags=DOMParsingTools.getChildTags(root);
    for(Element childTag : childTags)
    {
      String tagName=childTag.getTagName();
      if (StatsProviderXMLConstants.STAT_TAG.equals(tagName))
      {
        StatProvider statProvider=parseStatProvider(childTag,i18n);
        statsProvider.addStatProvider(statProvider);
      }
      else if (StatsProviderXMLConstants.SPECIAL_EFFECT_TAG.equals(tagName))
      {
        String label=DOMParsingTools.getStringAttribute(childTag.getAttributes(),StatsProviderXMLConstants.SPECIAL_EFFECT_LABEL_ATTR,null);
        label=I18nRuntimeUtils.getLabel(i18n,label);
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
   * @param i18n Labels manager.
   * @return A stat provider.
   */
  private static StatProvider parseStatProvider(Element root, SingleLocaleLabelsManager i18n)
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
      if ((i18n!=null) && (descriptionOverride!=null))
      {
        String i18nDescriptionOverride=i18n.getLabel(descriptionOverride);
        if (i18nDescriptionOverride!=null)
        {
          descriptionOverride=i18nDescriptionOverride;
        }
      }
      provider.setDescriptionOverride(descriptionOverride);

      // Modifiers
      String modifiersStr=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_MODIFIERS_ATTR,null);
      if (modifiersStr!=null)
      {
        ModPropertyList modifiers=ModPropertyListIO.fromPersistedString(modifiersStr);
        provider.setModifiers(modifiers);
      }
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
