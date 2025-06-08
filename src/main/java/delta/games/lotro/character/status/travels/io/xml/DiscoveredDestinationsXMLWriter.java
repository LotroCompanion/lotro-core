package delta.games.lotro.character.status.travels.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.travels.DiscoveredDestinations;
import delta.games.lotro.lore.travels.TravelDestination;

/**
 * Writes discovered destinations to an XML file.
 * @author DAM
 */
public class DiscoveredDestinationsXMLWriter
{
  /**
   * Write discovered destinations to an XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final DiscoveredDestinations data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write discovered destinations to the given XML stream.
   * @param hd XML output stream.
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  private void write(TransformerHandler hd, DiscoveredDestinations data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",DiscoveredDestinationsXMLConstants.DISCOVERED_DESTINATIONS_TAG,attrs);

    List<TravelDestination> destinations=data.getAll();
    for(TravelDestination destination : destinations)
    {
      AttributesImpl destinationAttrs=new AttributesImpl();
      // ID
      int id=destination.getIdentifier();
      destinationAttrs.addAttribute("","",DiscoveredDestinationsXMLConstants.DESTINATION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=destination.getName();
      if (name!=null)
      {
        destinationAttrs.addAttribute("","",DiscoveredDestinationsXMLConstants.DESTINATION_NAME_ATTR,XmlWriter.CDATA,name);
      }
      hd.startElement("","",DiscoveredDestinationsXMLConstants.DESTINATION_TAG,destinationAttrs);
      hd.endElement("","",DiscoveredDestinationsXMLConstants.DESTINATION_TAG);
    }
    hd.endElement("","",DiscoveredDestinationsXMLConstants.DISCOVERED_DESTINATIONS_TAG);
  }
}
