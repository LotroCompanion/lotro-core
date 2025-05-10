package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.WorldEventRequirement;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.utils.Proxy;

/**
 * XML I/O for world events requirements.
 * @author DAM
 */
public class WorldEventRequirementXMLIO implements RequirementSAXWriter<WorldEventRequirement>,RequirementXMLReader<WorldEventRequirement>
{
  @Override
  public WorldEventRequirement readSAX(Attributes attributes)
  {
    String input=SAXParsingTools.getStringAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_WORLD_EVENT_ATTR,null);
    return fromString(input);
  }

  @Override
  public WorldEventRequirement readDOM(NamedNodeMap attrs)
  {
    String input=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_WORLD_EVENT_ATTR,null);
    return fromString(input);
  }

  @Override
  public void write(AttributesImpl attrs, WorldEventRequirement worldEventRequirement)
  {
    if (worldEventRequirement!=null)
    {
      String str=asString(worldEventRequirement);
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_WORLD_EVENT_ATTR,XmlWriter.CDATA,str);
    }
  }

  private String asString(WorldEventRequirement requirement)
  {
    SimpleWorldEventCondition condition=requirement.getCondition();
    Proxy<WorldEvent> targetEvent=condition.getWorldEvent();
    // World event ID
    int targetEventID=targetEvent.getId();
    // Operator
    ComparisonOperator operator=condition.getOperator();
    // Value
    Integer value=condition.getValue();
    return targetEventID+" "+operator+" "+value;
  }

  private static WorldEventRequirement fromString(String input)
  {
    if (input==null)
    {
      return null;
    }
    String[] parts=input.split(" ");
    int targetEventID=NumericTools.parseInt(parts[0],0);
    Proxy<WorldEvent> proxy=new Proxy<WorldEvent>();
    proxy.setId(targetEventID);
    ComparisonOperator operator=ComparisonOperator.valueOf(parts[1]);
    int value=NumericTools.parseInt(parts[2],0);
    SimpleWorldEventCondition condition=new SimpleWorldEventCondition(operator,proxy,value);
    return new WorldEventRequirement(condition);
  }
}
