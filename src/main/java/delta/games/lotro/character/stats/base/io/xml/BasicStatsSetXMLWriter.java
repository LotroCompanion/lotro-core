package delta.games.lotro.character.stats.base.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Writes basic stats sets to XML files.
 * @author DAM
 */
public class BasicStatsSetXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param tagName Tag to use to wrap stat tags.
   * @param statsSet Stats to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, String tagName, BasicStatsSet statsSet) throws Exception
  {
    if (statsSet!=null)
    {
      hd.startElement("","",tagName,new AttributesImpl());
      writeStats(hd,statsSet);
      hd.endElement("","",tagName);
    }
  }

  /**
   * Write a set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param statsSet Stats to write.
   * @throws Exception If an error occurs.
   */
  public static void writeStats(TransformerHandler hd, BasicStatsSet statsSet) throws Exception
  {
    if (statsSet!=null)
    {
      for(StatDescription stat : statsSet.getSortedStats())
      {
        AttributesImpl statAttrs=new AttributesImpl();
        FixedDecimalsInteger value=statsSet.getStat(stat);
        String key=stat.getKey();
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_NAME_ATTR,CDATA,key);
        String valueStr=String.valueOf(value.getInternalValue());
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_VALUE_ATTR,CDATA,valueStr);
        hd.startElement("","",BasicStatsSetXMLConstants.STAT_TAG,statAttrs);
        hd.endElement("","",BasicStatsSetXMLConstants.STAT_TAG);
      }
    }
  }
}
