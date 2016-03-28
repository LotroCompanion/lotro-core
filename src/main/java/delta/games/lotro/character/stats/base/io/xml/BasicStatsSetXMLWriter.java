package delta.games.lotro.character.stats.base.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Writes basic stats sets to XML files.
 * @author DAM
 */
public class BasicStatsSetXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write an set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param statsSet Stats to write.
   * @throws Exception
   */
  public static void write(TransformerHandler hd, BasicStatsSet statsSet) throws Exception
  {
    if (statsSet!=null)
    {
      hd.startElement("","",BasicStatsSetXMLConstants.STATS_TAG,new AttributesImpl());
      for(STAT stat : STAT.values())
      {
        AttributesImpl statAttrs=new AttributesImpl();
        FixedDecimalsInteger value=statsSet.getStat(stat);
        if (value!=null)
        {
          String key=stat.getKey();
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_NAME_ATTR,CDATA,key);
          String valueStr=String.valueOf(value.getInternalValue());
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_VALUE_ATTR,CDATA,valueStr);
          hd.startElement("","",BasicStatsSetXMLConstants.STAT_TAG,statAttrs);
          hd.endElement("","",BasicStatsSetXMLConstants.STAT_TAG);
        }
      }
      hd.endElement("","",BasicStatsSetXMLConstants.STATS_TAG);
    }
  }
}
