package delta.games.lotro.character.stats.base.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.utils.NumericUtils;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Read a basic stats set from XML documents.
 * @author DAM
 */
public class BasicStatsSetXMLParser
{
  /**
   * Load a set of stats from a XML tag.
   * @param root Root tag.
   * @return A set of stats.
   */
  public static BasicStatsSet parseStats(Element root)
  {
    return parseStats(root,null);
  }

  /**
   * Load a set of stats from a XML tag.
   * @param root Root tag.
   * @param labelsMgr Labels manager (may be <code>null</code>).
   * @return A set of stats.
   */
  public static BasicStatsSet parseStats(Element root, SingleLocaleLabelsManager labelsMgr)
  {
    BasicStatsSet ret=new BasicStatsSet();
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,BasicStatsSetXMLConstants.STAT_TAG,false);
    for(Element statTag : statTags)
    {
      NamedNodeMap attrs=statTag.getAttributes();
      // Stat
      String statName=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_NAME_ATTR,"");
      String statValue=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_VALUE_ATTR,"");
      StatDescription stat=StatsRegistry.getInstance().getByKey(statName);
      // Operator
      StatOperator operator=StatOperator.ADD;
      String operatorStr=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_OPERATOR_ATTR,null);
      if (operatorStr!=null)
      {
        operator=StatOperator.valueOf(operatorStr);
      }
      // Value
      Number value=NumericUtils.fromPersistenceString(statValue);
      // Description override
      String descriptionOverride=DOMParsingTools.getStringAttribute(attrs,BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR,null);
      if ((descriptionOverride!=null) && (labelsMgr!=null))
      {
        descriptionOverride=I18nRuntimeUtils.getLabel(labelsMgr,descriptionOverride);
      }
      ret.setStat(stat,operator,value,descriptionOverride);
    }
    return ret;
  }
}
