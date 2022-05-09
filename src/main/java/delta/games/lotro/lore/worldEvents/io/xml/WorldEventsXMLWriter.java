package delta.games.lotro.lore.worldEvents.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.BooleanWorldEvent;
import delta.games.lotro.lore.worldEvents.ConditionWorldEvent;
import delta.games.lotro.lore.worldEvents.CountedWorldEvent;
import delta.games.lotro.lore.worldEvents.IntegerWorldEvent;
import delta.games.lotro.lore.worldEvents.WorldEvent;

/**
 * Writes some world events to an XML document.
 * @author DAM
 */
public class WorldEventsXMLWriter
{
  private static final Logger LOGGER=Logger.getLogger(WorldEventsXMLWriter.class);

  /**
   * Write some world events to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<WorldEvent> data, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",WorldEventsXMLConstants.WORLD_EVENTS_TAG,attrs);
        for(WorldEvent element : data)
        {
          writeWorldEvent(hd,element);
        }
        hd.endElement("","",WorldEventsXMLConstants.WORLD_EVENTS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private static void writeWorldEvent(TransformerHandler hd, WorldEvent worldEvent) throws Exception
  {
    if (worldEvent instanceof BooleanWorldEvent)
    {
      writeBooleanWorldEvent(hd,(BooleanWorldEvent)worldEvent);
    }
    else if (worldEvent instanceof ConditionWorldEvent)
    {
      writeConditionWorldEvent(hd,(ConditionWorldEvent)worldEvent);
    }
    else if (worldEvent instanceof IntegerWorldEvent)
    {
      writeIntegerWorldEvent(hd,(IntegerWorldEvent)worldEvent);
    }
    else if (worldEvent instanceof CountedWorldEvent)
    {
      writeCountedWorldEvent(hd,(CountedWorldEvent)worldEvent);
    }
    else
    {
      LOGGER.warn("Unmanaged world event: "+worldEvent);
    }
  }

  private static void writeBooleanWorldEvent(TransformerHandler hd, BooleanWorldEvent worldEvent) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    writeWorldEventAttrs(attrs,worldEvent);
    // Default
    boolean defaultValue=worldEvent.getDefaultValue();
    attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_BOOLEAN_DEFAULT_ATTR,XmlWriter.CDATA,String.valueOf(defaultValue));
    hd.startElement("","",WorldEventsXMLConstants.BOOLEAN_WORLD_EVENT_TAG,attrs);
    hd.endElement("","",WorldEventsXMLConstants.BOOLEAN_WORLD_EVENT_TAG);
  }

  private static void writeConditionWorldEvent(TransformerHandler hd, ConditionWorldEvent worldEvent) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    writeWorldEventAttrs(attrs,worldEvent);
    hd.startElement("","",WorldEventsXMLConstants.CONDITION_WORLD_EVENT_TAG,attrs);
    // Condition
    AbstractWorldEventCondition condition=worldEvent.getCondition();
    WorldEventConditionsXMLWriter.writeWorldEventCondition(hd,condition);
    hd.endElement("","",WorldEventsXMLConstants.CONDITION_WORLD_EVENT_TAG);
  }

  private static void writeIntegerWorldEvent(TransformerHandler hd, IntegerWorldEvent worldEvent) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    writeWorldEventAttrs(attrs,worldEvent);
    // Default
    Integer defaultValue=worldEvent.getDefaultValue();
    if (defaultValue!=null)
    {
      attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_INTEGER_DEFAULT_ATTR,XmlWriter.CDATA,defaultValue.toString());
    }
    // Min
    Integer min=worldEvent.getMinValue();
    if (min!=null)
    {
      attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MIN_ATTR,XmlWriter.CDATA,min.toString());
    }
    // Max
    Integer max=worldEvent.getMaxValue();
    if (max!=null)
    {
      attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MAX_ATTR,XmlWriter.CDATA,max.toString());
    }
    hd.startElement("","",WorldEventsXMLConstants.INTEGER_WORLD_EVENT_TAG,attrs);
    hd.endElement("","",WorldEventsXMLConstants.INTEGER_WORLD_EVENT_TAG);
  }

  private static void writeCountedWorldEvent(TransformerHandler hd, CountedWorldEvent worldEvent) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    writeWorldEventAttrs(attrs,worldEvent);
    // Max
    int max=worldEvent.getMax();
    attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_INTEGER_MAX_ATTR,XmlWriter.CDATA,String.valueOf(max));
    hd.startElement("","",WorldEventsXMLConstants.COUNTED_WORLD_EVENT_TAG,attrs);
    // Conditions
    for(AbstractWorldEventCondition condition : worldEvent.getConditions())
    {
      WorldEventConditionsXMLWriter.writeWorldEventCondition(hd,condition);
    }
    hd.endElement("","",WorldEventsXMLConstants.COUNTED_WORLD_EVENT_TAG);
  }

  private static void writeWorldEventAttrs(AttributesImpl attrs, WorldEvent worldEvent) throws Exception
  {
    // Identifier
    int id=worldEvent.getIdentifier();
    attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Property ID
    int propertyID=worldEvent.getPropertyID();
    attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_ID_ATTR,XmlWriter.CDATA,String.valueOf(propertyID));
    // Property name
    String propertyName=worldEvent.getPropertyName();
    attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_PROPERTY_NAME_ATTR,XmlWriter.CDATA,propertyName);
    // Description
    String description=worldEvent.getDescription();
    if (description!=null)
    {
      attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Progress
    String progress=worldEvent.getProgress();
    if (progress!=null)
    {
      attrs.addAttribute("","",WorldEventsXMLConstants.WORLD_EVENT_PROGRESS_ATTR,XmlWriter.CDATA,progress);
    }
  }
}
