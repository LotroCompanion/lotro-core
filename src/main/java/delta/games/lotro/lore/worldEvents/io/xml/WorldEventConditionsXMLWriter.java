package delta.games.lotro.lore.worldEvents.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.utils.Proxy;

/**
 * Writes some world event conditions to an XML document.
 * @author DAM
 */
public class WorldEventConditionsXMLWriter
{
  /**
   * Write a world event condition to the given stream.
   * @param hd Output stream.
   * @param condition Condition to write.
   * @throws Exception If an error occurs.
   */
  public static void writeWorldEventCondition(TransformerHandler hd, AbstractWorldEventCondition condition) throws Exception
  {
    if (condition instanceof CompoundWorldEventCondition)
    {
      writeCompoundWorldEventCondition(hd,(CompoundWorldEventCondition)condition);
    }
    else if (condition instanceof SimpleWorldEventCondition)
    {
      writeSimpleWorldEventCondition(hd,(SimpleWorldEventCondition)condition);
    }
  }

  /**
   * Write a compound condition.
   * @param hd Output stream.
   * @param condition Data to write.
   * @throws Exception If an error occurs.
   */
  private static void writeCompoundWorldEventCondition(TransformerHandler hd, CompoundWorldEventCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    Operator operator=condition.getOperator();
    attrs.addAttribute("","",WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    hd.startElement("","",WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG,attrs);
    for(AbstractWorldEventCondition item : condition.getItems())
    {
      writeWorldEventCondition(hd,item);
    }
    hd.endElement("","",WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG);
  }

  /**
   * Write a simple world event condition.
   * @param hd Output stream.
   * @param item Data to write.
   * @throws SAXException If an error occurs.
   */
  private static void writeSimpleWorldEventCondition(TransformerHandler hd, SimpleWorldEventCondition item) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Proxy<WorldEvent> targetEvent=item.getWorldEvent();
    // World event ID
    int targetEventID=targetEvent.getId();
    attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(targetEventID));
    // World event name
    String targetEventName=targetEvent.getName();
    attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_NAME_ATTR,XmlWriter.CDATA,targetEventName);
    // Operator
    ComparisonOperator operator=item.getOperator();
    attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    // Value
    Integer value=item.getValue();
    if (value!=null)
    {
      attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_WORLD_EVENT_VALUE_ATTR,XmlWriter.CDATA,value.toString());
    }
    // Compare to
    Proxy<WorldEvent> compareToWorldEvent=item.getCompareToWorldEvent();
    if (compareToWorldEvent!=null)
    {
      // World event ID
      int compareToEventID=compareToWorldEvent.getId();
      attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_COMPARE_TO_WORLD_EVENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(compareToEventID));
      // World event name
      String compareToEventName=compareToWorldEvent.getName();
      attrs.addAttribute("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_COMPARE_TO_WORLD_EVENT_NAME_ATTR,XmlWriter.CDATA,compareToEventName);
    }
    hd.startElement("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG,attrs);
    hd.endElement("","",WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG);
  }
}
