package delta.games.lotro.character.status.travels.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.travels.DiscoveredDestinations;
import delta.games.lotro.lore.travels.TravelDestination;
import delta.games.lotro.lore.travels.TravelsManager;

/**
 * Parser for the discovered destinations stored in XML.
 * @author DAM
 */
public class DiscoveredDestinationsXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(DiscoveredDestinationsXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public DiscoveredDestinations read(File source)
  {
    DiscoveredDestinations status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=read(root);
    }
    return status;
  }

  private DiscoveredDestinations read(Element root)
  {
    DiscoveredDestinations ret=new DiscoveredDestinations();
    TravelsManager mgr=TravelsManager.getInstance();
    List<Element> destinationTags=DOMParsingTools.getChildTagsByName(root,DiscoveredDestinationsXMLConstants.DESTINATION_TAG,false);
    for(Element destinationTag : destinationTags)
    {
      NamedNodeMap attrs=destinationTag.getAttributes();
      int destinationID=DOMParsingTools.getIntAttribute(attrs,DiscoveredDestinationsXMLConstants.DESTINATION_ID_ATTR,0);
      TravelDestination destination=mgr.getDestination(destinationID);
      if (destination!=null)
      {
        ret.addKnownDestination(destination);
      }
      else
      {
        
        LOGGER.warn("Unknown destination: {}",Integer.valueOf(destinationID));
      }
    }
    return ret;
  }
}
