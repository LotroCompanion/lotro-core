package delta.games.lotro.lore.worldEvents.io.xml;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.lore.worldEvents.WorldEventConditionsRenderer;
import delta.games.lotro.lore.worldEvents.WorldEventsManager;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for world event conditions.
 * @author DAM
 */
public class WorldEventConditionsSaxParser
{
  private static final Logger LOGGER=Logger.getLogger(WorldEventConditionsSaxParser.class);

  private WorldEventConditionsRenderer _renderer;
  private AbstractWorldEventCondition _result;
  private CompoundWorldEventCondition _compound;

  /**
   * Constructor.
   */
  public WorldEventConditionsSaxParser()
  {
    _renderer=new WorldEventConditionsRenderer();
  }

  /**
   * Get the result data.
   * @return the result data.
   */
  public AbstractWorldEventCondition getResult()
  {
    return _result;
  }

  /**
   * Handle a start element event.
   * @param tagName Name of started tag.
   * @param attrs Tag attributes.
   */
  public void startElement(String tagName, Attributes attrs)
  {
    if (WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      SimpleWorldEventCondition condition=parseSimpleWorldEventCondition(attrs);
      if (_compound!=null)
      {
        _compound.addItem(condition);
      }
      else
      {
        _result=condition;
      }
    }
    else if (WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      parseCompoundWorldEventCondition(attrs);
      _result=_compound;
    }
  }

  private SimpleWorldEventCondition parseSimpleWorldEventCondition(Attributes attrs)
  {
    SimpleWorldEventCondition ret;
    // Operator
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_OPERATOR_ATTR,"");
    ComparisonOperator operator=ComparisonOperator.valueOf(operatorStr);
    // Target world event
    int targetWorldEventID=SAXParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_ID_ATTR,0);
    Proxy<WorldEvent> targetWorldEvent=new Proxy<WorldEvent>();
    targetWorldEvent.setId(targetWorldEventID);
    // Value
    String valueStr=attrs.getValue(WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_VALUE_ATTR);
    if (valueStr!=null)
    {
      int value=SAXParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_VALUE_ATTR,0);
      ret=new SimpleWorldEventCondition(operator,targetWorldEvent,value);
    }
    else
    {
      // Compare To world event
      int compareToWorldEventID=SAXParsingTools.getIntAttribute(attrs,WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_COMPARE_TO_WORLD_EVENT_ID_ATTR,0);
      Proxy<WorldEvent> compareToWorldEvent=new Proxy<WorldEvent>();
      compareToWorldEvent.setId(compareToWorldEventID);
      ret=new SimpleWorldEventCondition(operator,targetWorldEvent,compareToWorldEvent);
    }
    resolveCondition(ret);
    String label=_renderer.renderSimpleWorldEventCondition(ret);
    ret.setLabel(label);
    return ret;
  }

  private void parseCompoundWorldEventCondition(Attributes attrs)
  {
    // Operator
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_OPERATOR_ATTR,"");
    Operator operator=Operator.valueOf(operatorStr);
    if (_compound!=null)
    {
      LOGGER.warn("Found multiple levels of compound world event conditions!");
    }
    _compound=new CompoundWorldEventCondition(operator);
  }

  private void resolveCondition(SimpleWorldEventCondition condition)
  {
    Proxy<WorldEvent> targetWorldEvent=condition.getWorldEvent();
    resolveWorldEventProxy(targetWorldEvent);
    Proxy<WorldEvent> compareToWorldEvent=condition.getCompareToWorldEvent();
    resolveWorldEventProxy(compareToWorldEvent);
  }

  private void resolveWorldEventProxy(Proxy<WorldEvent> proxy)
  {
    if (proxy==null)
    {
      return;
    }
    WorldEventsManager mgr=WorldEventsManager.getInstance();
    WorldEvent worldEvent=mgr.getWorldEvent(proxy.getId());
    if (worldEvent!=null)
    {
      proxy.setObject(worldEvent);
      proxy.setName(worldEvent.getPropertyName());
    }
  }
}
