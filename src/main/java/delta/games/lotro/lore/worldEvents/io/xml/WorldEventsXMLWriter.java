package delta.games.lotro.lore.worldEvents.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.worldEvents.WorldEvent;

/**
 * Writes some world events to an XML document.
 * @author DAM
 */
public class WorldEventsXMLWriter
{
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
    AttributesImpl attrs=new AttributesImpl();
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
    hd.startElement("","",WorldEventsXMLConstants.WORLD_EVENT_TAG,attrs);
    // TODO Conditions
    hd.endElement("","",WorldEventsXMLConstants.WORLD_EVENT_TAG);
  }
}
