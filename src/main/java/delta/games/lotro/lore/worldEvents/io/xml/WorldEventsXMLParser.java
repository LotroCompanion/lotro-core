package delta.games.lotro.lore.worldEvents.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.BooleanWorldEvent;
import delta.games.lotro.lore.worldEvents.ConditionWorldEvent;
import delta.games.lotro.lore.worldEvents.CountedWorldEvent;
import delta.games.lotro.lore.worldEvents.IntegerWorldEvent;
import delta.games.lotro.lore.worldEvents.WorldEvent;

/**
 * Parser for world events stored in XML.
 * @author DAM
 */
public class WorldEventsXMLParser
{
  private WorldEventConditionsXMLParser _worldEventConditionsParser=new WorldEventConditionsXMLParser();

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded world events or <code>null</code>.
   */
  public List<WorldEvent> parseXML(File source)
  {
    List<WorldEvent> ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseWorldEvents(root);
    }
    return ret;
  }

  private List<WorldEvent> parseWorldEvents(Element mainTag)
  {
    List<WorldEvent> ret=new ArrayList<WorldEvent>();
    List<Element> elementTags=DOMParsingTools.getChildTags(mainTag);
    for(Element elementTag : elementTags)
    {
      WorldEvent worldEvent=parseWorldEvent(elementTag);
      if (worldEvent!=null)
      {
        ret.add(worldEvent);
      }
    }
    return ret;
  }

  /**
   * Read a world event from a tag.
   * @param elementTag Tag to read.
   * @return the loaded world event or <code>null</code>.
   */
  private WorldEvent parseWorldEvent(Element elementTag)
  {
    String tagName=elementTag.getNodeName();
    if (WorldEventsXMLConstants.BOOLEAN_WORLD_EVENT_TAG.equals(tagName))
    {
      return parseBooleanWorldEvent(elementTag);
    }
    if (WorldEventsXMLConstants.CONDITION_WORLD_EVENT_TAG.equals(tagName))
    {
      return parseConditionWorldEvent(elementTag);
    }
    if (WorldEventsXMLConstants.INTEGER_WORLD_EVENT_TAG.equals(tagName))
    {
      return parseIntegerWorldEvent(elementTag);
    }
    if (WorldEventsXMLConstants.COUNTED_WORLD_EVENT_TAG.equals(tagName))
    {
      return parseCountedWorldEvent(elementTag);
    }
    return null;
  }

  private BooleanWorldEvent parseBooleanWorldEvent(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    BooleanWorldEvent ret=new BooleanWorldEvent();
    parseWorldEventAttrs(attrs,ret);
    // Default
    boolean defaultValue=DOMParsingTools.getBooleanAttribute(attrs,WorldEventsXMLConstants.BOOLEAN_WORLD_EVENT_TAG,false);
    ret.setDefaultValue(defaultValue);
    return ret;
  }

  private ConditionWorldEvent parseConditionWorldEvent(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    ConditionWorldEvent ret=new ConditionWorldEvent();
    parseWorldEventAttrs(attrs,ret);
    // Condition
    List<Element> childTags=DOMParsingTools.getChildTags(elementTag);
    if (childTags.size()==1)
    {
      AbstractWorldEventCondition condition=_worldEventConditionsParser.parseCondition(elementTag);
      ret.setCondition(condition);
    }
    return ret;
  }

  private IntegerWorldEvent parseIntegerWorldEvent(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    IntegerWorldEvent ret=new IntegerWorldEvent();
    parseWorldEventAttrs(attrs,ret);
    // Default
    if (attrs.getNamedItem(WorldEventsXMLConstants.WORLD_EVENT_INTEGER_DEFAULT_ATTR)!=null)
    {
      int defaultValue=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_INTEGER_DEFAULT_ATTR,0);
      ret.setDefaultValue(Integer.valueOf(defaultValue));
    }
    // Min
    if (attrs.getNamedItem(WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MIN_ATTR)!=null)
    {
      int minValue=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MIN_ATTR,0);
      ret.setMinValue(Integer.valueOf(minValue));
    }
    // Max
    if (attrs.getNamedItem(WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MAX_ATTR)!=null)
    {
      int maxValue=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MAX_ATTR,0);
      ret.setMaxValue(Integer.valueOf(maxValue));
    }
    return ret;
  }

  private CountedWorldEvent parseCountedWorldEvent(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    CountedWorldEvent ret=new CountedWorldEvent();
    parseWorldEventAttrs(attrs,ret);
    // Max
    int maxValue=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MAX_ATTR,0);
    ret.setMax(maxValue);
    List<Element> childTags=DOMParsingTools.getChildTags(elementTag);
    for(Element childTag : childTags)
    {
      AbstractWorldEventCondition condition=_worldEventConditionsParser.parseCondition(childTag);
      if (condition!=null)
      {
        ret.addCondition(condition);
      }
    }
    return ret;
  }

  private void parseWorldEventAttrs(NamedNodeMap attrs, WorldEvent ret)
  {
    // Identifier
    int identifier=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_ID_ATTR,0);
    ret.setIdentifier(identifier);
    // Property ID
    int propertyID=DOMParsingTools.getIntAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_ID_ATTR,0);
    ret.setPropertyID(propertyID);
    // Property name
    String propertyName=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_NAME_ATTR,"");
    ret.setPropertyName(propertyName);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_DESCRIPTION_ATTR,null);
    ret.setDescription(description);
    // Progress
    String progress=DOMParsingTools.getStringAttribute(attrs,WorldEventsXMLConstants.WORLD_EVENT_PROGRESS_ATTR,null);
    ret.setProgress(progress);
  }
}
