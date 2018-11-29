package delta.games.lotro.common.stats.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
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
   * @param statsProvider Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeXml(TransformerHandler hd, StatsProvider statsProvider) throws SAXException
  {
    int nbStats=statsProvider.getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider provider=statsProvider.getStatProvider(i);
      writeProvider(hd,provider);
    }
  }

  private static void writeProvider(TransformerHandler hd, StatProvider provider) throws SAXException
  {
    STAT stat=provider.getStat();
    AttributesImpl attrs=new AttributesImpl();
    attrs.addAttribute("","",StatsProviderXMLConstants.STAT_NAME_ATTR,XmlWriter.CDATA,stat.getKey());
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
    hd.startElement("","",StatsProviderXMLConstants.STAT_TAG,attrs);
    hd.endElement("","",StatsProviderXMLConstants.STAT_TAG);
  }
}
