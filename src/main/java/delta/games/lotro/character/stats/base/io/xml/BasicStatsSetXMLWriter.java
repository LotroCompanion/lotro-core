package delta.games.lotro.character.stats.base.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
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
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, String tagName, BasicStatsSet statsSet) throws SAXException
  {
    if ((statsSet!=null) && (statsSet.getStatsCount()>0))
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
   * @throws SAXException If an error occurs.
   */
  public static void writeStats(TransformerHandler hd, BasicStatsSet statsSet) throws SAXException
  {
    if (statsSet!=null)
    {
      for(StatsSetElement element : statsSet.getStatElements())
      {
        AttributesImpl statAttrs=new AttributesImpl();
        // Stat
        StatDescription stat=element.getStat();
        String key=stat.getPersistenceKey();
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_NAME_ATTR,CDATA,key);
        // Operator
        StatOperator operator=element.getOperator();
        if (operator!=StatOperator.ADD)
        {
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_OPERATOR_ATTR,CDATA,operator.name());
        }
        // Description override
        String descriptionOverride=element.getDescriptionOverride();
        if ((descriptionOverride!=null) && (descriptionOverride.length()>0))
        {
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR,CDATA,descriptionOverride);
        }
        // Value
        FixedDecimalsInteger value=element.getValue();
        String valueStr=String.valueOf(value.getInternalValue());
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_VALUE_ATTR,CDATA,valueStr);
        hd.startElement("","",BasicStatsSetXMLConstants.STAT_TAG,statAttrs);
        hd.endElement("","",BasicStatsSetXMLConstants.STAT_TAG);
      }
    }
  }
}
