package delta.games.lotro.common.stats.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.RangedStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.utils.FixedDecimalsInteger;
import delta.games.lotro.utils.maths.Progression;

/**
 * XML writer for stats providers.
 * @author DAM
 */
public class StatsProviderXMLWriter
{
  /**
   * Write a stats provider to a XML document.
   * @param hd Output.
   * @param tag Wrapper tag to use, may be <code>null</code>.
   * @param statsProvider Data to write.
   * @param stats Stats values to write, may be <code>null</code>.
   * @throws SAXException If an error occurs.
   */
  public static void writeXml(TransformerHandler hd, String tag, StatsProvider statsProvider, BasicStatsSet stats) throws SAXException
  {
    if (tag!=null)
    {
      hd.startElement("","",tag,new AttributesImpl());
    }
    int nbStats=statsProvider.getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider provider=statsProvider.getStatProvider(i);
      STAT stat=provider.getStat();
      FixedDecimalsInteger statValue=(stats!=null)?stats.getStat(stat):null;
      writeProvider(hd,provider,statValue);
    }
    if (tag!=null)
    {
      hd.endElement("","",tag);
    }
  }

  /**
   * Write a single stat provider.
   * @param hd Output.
   * @param provider Provider to write.
   * @param statValue Stat value to write, may be <code>null</code>.
   * @throws SAXException If an error occurs.
   */
  private static void writeProvider(TransformerHandler hd, StatProvider provider, FixedDecimalsInteger statValue) throws SAXException
  {
    STAT stat=provider.getStat();
    AttributesImpl attrs=new AttributesImpl();
    attrs.addAttribute("","",StatsProviderXMLConstants.STAT_NAME_ATTR,XmlWriter.CDATA,stat.getKey());
    if (statValue!=null)
    {
      String valueStr=String.valueOf(statValue.getInternalValue());
      attrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_VALUE_ATTR,XmlWriter.CDATA,valueStr);
    }
    StatOperator operator=provider.getOperator();
    if (operator!=StatOperator.ADD)
    {
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    }
    if (provider instanceof ConstantStatProvider)
    {
      ConstantStatProvider constantProvider=(ConstantStatProvider)provider;
      float value=constantProvider.getValue();
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_CONSTANT_ATTR,XmlWriter.CDATA,String.valueOf(value));
    }
    else if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableProvider=(ScalableStatProvider)provider;
      Progression progression=scalableProvider.getProgression();
      int progressionId=progression.getIdentifier();
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_SCALING_ATTR,XmlWriter.CDATA,String.valueOf(progressionId));
    }
    else if (provider instanceof TieredScalableStatProvider)
    {
      TieredScalableStatProvider tieredStatProvider=(TieredScalableStatProvider)provider;
      int nbTiers=tieredStatProvider.getNumberOfTiers();
      StringBuilder sb=new StringBuilder();
      for(int i=1;i<=nbTiers;i++)
      {
        Progression progression=tieredStatProvider.getProgression(i);
        if (sb.length()>0) sb.append(";");
        sb.append(progression.getIdentifier());
      }
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_TIERED_SCALING_ATTR,XmlWriter.CDATA,sb.toString());
    }
    else if (provider instanceof RangedStatProvider)
    {
      String providerDefinition=buildRangedStatProviderDefinition((RangedStatProvider)provider);
      attrs.addAttribute("","",StatsProviderXMLConstants.STAT_RANGED_ATTR,XmlWriter.CDATA,providerDefinition);
    }
    hd.startElement("","",StatsProviderXMLConstants.STAT_TAG,attrs);
    hd.endElement("","",StatsProviderXMLConstants.STAT_TAG);
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
    }
    return sb.toString();
  }
}
