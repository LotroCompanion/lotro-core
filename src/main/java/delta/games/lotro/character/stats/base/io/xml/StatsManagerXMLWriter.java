package delta.games.lotro.character.stats.base.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.CustomStatsMergeMode;
import delta.games.lotro.common.stats.StatsManager;

/**
 * Writes stats managers to XML files.
 * @author DAM
 */
public class StatsManagerXMLWriter
{
  /**
   * Write a stats manager to the given XML stream.
   * @param hd XML output stream.
   * @param statsManager Stats to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, StatsManager statsManager) throws Exception
  {
    AttributesImpl statsAttrs=new AttributesImpl();
    // Mode
    CustomStatsMergeMode mode=statsManager.getMode();
    statsAttrs.addAttribute("","",StatsManagerXMLConstants.STATS_MODE_ATTR,XmlWriter.CDATA,mode.name());
    hd.startElement("","",BasicStatsSetXMLConstants.STATS_TAG,statsAttrs);

    // Default stats
    BasicStatsSet defaultStats=statsManager.getDefault();
    BasicStatsSetXMLWriter.write(hd,StatsManagerXMLConstants.DEFAULT_STATS_TAG,defaultStats);
    // Custom stats
    BasicStatsSet customStats=statsManager.getCustom();
    BasicStatsSetXMLWriter.write(hd,StatsManagerXMLConstants.CUSTOM_STATS_TAG,customStats);
    // Result stats
    BasicStatsSet resultStats=statsManager.getResult();
    BasicStatsSetXMLWriter.write(hd,StatsManagerXMLConstants.RESULT_STATS_TAG,resultStats);

    hd.endElement("","",BasicStatsSetXMLConstants.STATS_TAG);
  }
}
