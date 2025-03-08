package delta.games.lotro.character.stats.base.io.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.character.stats.StatsSetElementComparator;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.utils.NumericUtils;

/**
 * Writes basic stats sets to XML files.
 * @author DAM
 */
public class BasicStatsSetXMLWriter
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BasicStatsSetXMLWriter.class);

  /**
   * Write a set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param tagName Tag to use to wrap stat tags.
   * @param statsSet Stats to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, String tagName, BasicStatsSet statsSet) throws SAXException
  {
    write(hd,tagName,statsSet,false);
  }

  /**
   * Write a set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param tagName Tag to use to wrap stat tags.
   * @param statsSet Stats to write.
   * @param sort Sort stats or not.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, String tagName, BasicStatsSet statsSet, boolean sort) throws SAXException
  {
    if ((statsSet!=null) && (statsSet.getStatsCount()>0))
    {
      hd.startElement("","",tagName,new AttributesImpl());
      writeStats(hd,statsSet,sort);
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
    writeStats(hd,statsSet,false);
  }

  /**
   * Write a set of stats to the given XML stream.
   * @param hd XML output stream.
   * @param statsSet Stats to write.
   * @param sort Sort stats or not.
   * @throws SAXException If an error occurs.
   */
  public static void writeStats(TransformerHandler hd, BasicStatsSet statsSet, boolean sort) throws SAXException
  {
    if (statsSet!=null)
    {
      List<StatsSetElement> elements=new ArrayList<StatsSetElement>(statsSet.getStatElements());
      if (sort)
      {
        Collections.sort(elements,new StatsSetElementComparator());
      }
      for(StatsSetElement element : elements)
      {
        // Value
        Number value=element.getValue();
        String valueStr=NumericUtils.toPersistenceString(value);
        if (valueStr==null)
        {
          LOGGER.warn("Could not persist stat: {} (valueStr is null)",element);
          continue;
        }
        AttributesImpl statAttrs=new AttributesImpl();
        // Stat
        StatDescription stat=element.getStat();
        String key=stat.getPersistenceKey();
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_NAME_ATTR,XmlWriter.CDATA,key);
        // Operator
        StatOperator operator=element.getOperator();
        if ((operator!=null) && (operator!=StatOperator.ADD))
        {
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
        }
        // Description override
        String descriptionOverride=element.getDescriptionOverride();
        if ((descriptionOverride!=null) && (!descriptionOverride.isEmpty()))
        {
          statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR,XmlWriter.CDATA,descriptionOverride);
        }
        // Value
        statAttrs.addAttribute("","",BasicStatsSetXMLConstants.STAT_VALUE_ATTR,XmlWriter.CDATA,valueStr);
        hd.startElement("","",BasicStatsSetXMLConstants.STAT_TAG,statAttrs);
        hd.endElement("","",BasicStatsSetXMLConstants.STAT_TAG);
      }
    }
  }
}
