package delta.games.lotro.common.stats.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.GenericConstantStatProvider;
import delta.games.lotro.common.stats.RangedStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsProviderEntry;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.utils.maths.Progression;

/**
 * XML writer for stats providers.
 * @author DAM
 */
public class StatsProviderXMLWriter
{
  private static final Logger LOGGER=LoggerFactory.getLogger(StatsProviderXMLWriter.class);

  /**
   * Write a stats provider to a XML document.
   * @param hd Output.
   * @param statsProvider Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeXml(TransformerHandler hd, StatsProvider statsProvider) throws SAXException
  {
    int nbEntries=statsProvider.getEntriesCount();
    for(int i=0;i<nbEntries;i++)
    {
      StatsProviderEntry entry=statsProvider.getEntry(i);
      if (entry instanceof StatProvider)
      {
        writeProvider(hd,(StatProvider)entry);
      }
      else if (entry instanceof SpecialEffect)
      {
        SpecialEffect specialEffect=(SpecialEffect)entry;
        AttributesImpl attrs=new AttributesImpl();
        String label=specialEffect.getLabel();
        attrs.addAttribute("","",StatsProviderXMLConstants.SPECIAL_EFFECT_LABEL_ATTR,XmlWriter.CDATA,label);
        hd.startElement("","",StatsProviderXMLConstants.SPECIAL_EFFECT_TAG,attrs);
        hd.endElement("","",StatsProviderXMLConstants.SPECIAL_EFFECT_TAG);
      }
    }
  }

  /**
   * Write a single stat provider.
   * @param hd Output.
   * @param provider Provider to write.
   * @throws SAXException If an error occurs.
   */
  private static void writeProvider(TransformerHandler hd, StatProvider provider) throws SAXException
  {
    StatDescription stat=provider.getStat();
    AttributesImpl attrs=new AttributesImpl();
    // Stat ID
    attrs.addAttribute("","",StatsProviderXMLConstants.STAT_NAME_ATTR,XmlWriter.CDATA,stat.getPersistenceKey());
    // Stat operator
    StatOperator operator=provider.getOperator();
    if ((operator!=null) && (operator!=StatOperator.ADD))
    {
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    }
    // Description override
    String descriptionOverride=provider.getDescriptionOverride();
    if ((descriptionOverride!=null) && (!descriptionOverride.isEmpty()))
    {
      attrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR,XmlWriter.CDATA,descriptionOverride);
    }
    // Modifiers
    String modifiers=ModPropertyListIO.asPersistentString(provider.getModifiers());
    if (!modifiers.isEmpty())
    {
      attrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_MODIFIERS_ATTR,XmlWriter.CDATA,modifiers);
    }
    // Constant?
    if (provider instanceof ConstantStatProvider)
    {
      ConstantStatProvider constantProvider=(ConstantStatProvider)provider;
      float value=constantProvider.getValue();
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_CONSTANT_ATTR,XmlWriter.CDATA,String.valueOf(value));
    }
    // Generic constant?
    else if (provider instanceof GenericConstantStatProvider)
    {
      GenericConstantStatProvider<?> constantProvider=(GenericConstantStatProvider<?>)provider;
      String valueStr=constantProvider.asPersistentString();
      if (valueStr!=null)
      {
        attrs.addAttribute("","",StatsProviderXMLConstants.STAT_VALUE_ATTR,XmlWriter.CDATA,valueStr);
      }
    }
    // Scalable?
    else if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableProvider=(ScalableStatProvider)provider;
      Progression progression=scalableProvider.getProgression();
      if (progression!=null)
      {
        int progressionId=progression.getIdentifier();
        attrs.addAttribute("","",StatsProviderXMLConstants.STAT_SCALING_ATTR,XmlWriter.CDATA,String.valueOf(progressionId));
      }
      else
      {
        LOGGER.warn("Progression not found for a scalable stats provider!");
      }
    }
    // Tiered and scalable?
    else if (provider instanceof TieredScalableStatProvider)
    {
      TieredScalableStatProvider tieredStatProvider=(TieredScalableStatProvider)provider;
      writeTieredScalableStatProvider(tieredStatProvider,attrs);
    }
    // Ranged?
    else if (provider instanceof RangedStatProvider)
    {
      String providerDefinition=buildRangedStatProviderDefinition((RangedStatProvider)provider);
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_RANGED_ATTR,XmlWriter.CDATA,providerDefinition);
    }
    hd.startElement("","",StatsProviderXMLConstants.STAT_TAG,attrs);
    hd.endElement("","",StatsProviderXMLConstants.STAT_TAG);
  }

  private static void writeTieredScalableStatProvider(TieredScalableStatProvider tieredStatProvider, AttributesImpl attrs)
  {
    int nbTiers=tieredStatProvider.getNumberOfTiers();
    StringBuilder sb=new StringBuilder();
    for(int i=1;i<=nbTiers;i++)
    {
      Progression progression=tieredStatProvider.getProgression(i);
      if (progression!=null)
      {
        if (sb.length()>0) sb.append(";");
        sb.append(progression.getIdentifier());
      }
      else
      {
        LOGGER.warn("Progression not found for a tiered scalable stats provider!");
      }
    }
    attrs.addAttribute("","",StatsProviderXMLConstants.STAT_TIERED_SCALING_ATTR,XmlWriter.CDATA,sb.toString());
  }

  private static String buildRangedStatProviderDefinition(RangedStatProvider provider)
  {
    StringBuilder sb=new StringBuilder();
    int nbRanges=provider.getNumberOfRanges();
    for(int rangeIndex=0;rangeIndex<nbRanges;rangeIndex++)
    {
      if (rangeIndex>0) sb.append(',');
      Integer minLevel=provider.getMinimumLevel(rangeIndex);
      if (minLevel!=null)
      {
        sb.append(minLevel);
      }
      sb.append('-');
      Integer maxLevel=provider.getMaximumLevel(rangeIndex);
      if (maxLevel!=null)
      {
        sb.append(maxLevel);
      }
      sb.append(':');
      StatProvider statProvider=provider.getStatProvider(rangeIndex);
      if (statProvider instanceof ScalableStatProvider)
      {
        ScalableStatProvider scalableProvider=(ScalableStatProvider)statProvider;
        int progressionId=scalableProvider.getProgression().getIdentifier();
        sb.append(progressionId);
      }
      else
      {
        LOGGER.warn("A ranged stat provider does not use a ScalableStatProvider: {}",statProvider);
      }
    }
    return sb.toString();
  }
}
