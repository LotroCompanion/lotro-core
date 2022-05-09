package delta.games.lotro.lore.worldEvents.io.xml;

import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for world event conditions.
 * @author DAM
 */
public class WorldEventConditionsXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(WorldEventConditionsXMLParser.class);

  /**
   * Parse a world event condition from a tag.
   * @param elementTag Source tag.
   * @return A condition or <code>null</code> if not supported.
   */
  public static AbstractWorldEventCondition parseCondition(Element elementTag)
  {
    String tagName=elementTag.getNodeName();
    if (WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      return parseCompoundWorldEventCondition(elementTag);
    }
    if (WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      return parseSimpleWorldEventCondition(elementTag);
    }
    return null;
  }

  private static SimpleWorldEventCondition parseSimpleWorldEventCondition(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Operator
    String operatorStr=DOMParsingTools.getStringAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_OPERATOR_ATTR,"");
    ComparisonOperator operator=ComparisonOperator.valueOf(operatorStr);
    // Target world event
    int targetWorldEventID=DOMParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_ID_ATTR,0);
    Proxy<WorldEvent> targetWorldEvent=new Proxy<WorldEvent>();
    targetWorldEvent.setId(targetWorldEventID);
    // Value
    Node valueNode=attrs.getNamedItem(WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_VALUE_ATTR);
    if (valueNode!=null)
    {
      int value=DOMParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_VALUE_ATTR,0);
      SimpleWorldEventCondition ret=new SimpleWorldEventCondition(operator,targetWorldEvent,value);
      return ret;
    }
    // Compare To world event
    int compareToWorldEventID=DOMParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_COMPARE_TO_WORLD_EVENT_ID_ATTR,0);
    Proxy<WorldEvent> compareToWorldEvent=new Proxy<WorldEvent>();
    compareToWorldEvent.setId(compareToWorldEventID);
    SimpleWorldEventCondition ret=new SimpleWorldEventCondition(operator,targetWorldEvent,compareToWorldEvent);
    return ret;
  }

  private static CompoundWorldEventCondition parseCompoundWorldEventCondition(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Operator
    String operatorStr=DOMParsingTools.getStringAttribute(attrs,WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_OPERATOR_ATTR,"");
    Operator operator=Operator.valueOf(operatorStr);
    CompoundWorldEventCondition ret=new CompoundWorldEventCondition(operator);
    // Conditions
    List<Element> childTags=DOMParsingTools.getChildTags(elementTag);
    for(Element childTag : childTags)
    {
      AbstractWorldEventCondition childCondition=parseCondition(childTag);
      if (childCondition!=null)
      {
        ret.addItem(childCondition);
      }
      else
      {
        LOGGER.warn("Could not load condition from tag: "+childTag.getNodeName());
      }
    }
    return ret;
  }
}
