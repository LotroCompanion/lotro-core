package delta.games.lotro.lore.worldEvents.io.xml;

import java.util.ArrayDeque;
import java.util.Deque;

import org.xml.sax.Attributes;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.lore.worldEvents.WorldEventConditionsRenderer;
import delta.games.lotro.utils.Proxy;

/**
 * Read world event conditions from XML documents.
 * @author DAM
 */
public class WorldEventConditionsSaxParser extends SAXParserValve<AbstractWorldEventCondition>
{
  private WorldEventConditionsRenderer _renderer;
  private Deque<CompoundWorldEventCondition> _compounds=new ArrayDeque<CompoundWorldEventCondition>();
  private CompoundWorldEventCondition _compound;
  private WorldEventConditionsResolver _resolver;

  /**
   * Constructor.
   */
  public WorldEventConditionsSaxParser()
  {
    _renderer=new WorldEventConditionsRenderer();
    _resolver=new WorldEventConditionsResolver();
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
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
        setResult(condition);
      }
    }
    else if (WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      CompoundWorldEventCondition compound=parseCompoundWorldEventCondition(attrs);
      if (_compound==null)
      {
        setResult(compound);
      }
      else
      {
        _compound.addItem(compound);
      }
      _compound=compound;
      _compounds.push(_compound);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      _compound=_compounds.pop();
      if (_compounds.isEmpty())
      {
        _compound=null;
        return getParent();
      }
      _compound=_compounds.peek();
    }
    else if (WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG.equals(tagName))
    {
      if (_compound==null)
      {
        return getParent();
      }
    }
    return this;
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
    _resolver.resolve(ret);
    String label=_renderer.renderSimpleWorldEventCondition(ret);
    ret.setLabel(label);
    return ret;
  }

  private CompoundWorldEventCondition parseCompoundWorldEventCondition(Attributes attrs)
  {
    // Operator
    Operator operator=Operator.AND;
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_OPERATOR_ATTR,"");
    if (operatorStr!=null)
    {
      operator=Operator.valueOf(operatorStr);
    }
    CompoundWorldEventCondition compound=new CompoundWorldEventCondition(operator);
    return compound;
  }
}
