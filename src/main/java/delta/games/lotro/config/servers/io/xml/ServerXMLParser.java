package delta.games.lotro.config.servers.io.xml;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.config.servers.ServerDescription;

/**
 * Parser for server descriptions stored in XML.
 * @author DAM
 */
public class ServerXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ServerXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed servers.
   */
  public List<ServerDescription> parseXML(File source)
  {
    List<ServerDescription> ret=new ArrayList<ServerDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> serverTags=DOMParsingTools.getChildTagsByName(root,ServerXMLConstants.SERVER_TAG);
      for(Element serverTag : serverTags)
      {
        ServerDescription server=parseServer(serverTag);
        ret.add(server);
      }
    }
    return ret;
  }

  private ServerDescription parseServer(Element root)
  {
    ServerDescription server=new ServerDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,ServerXMLConstants.SERVER_NAME_ATTR,"");
    server.setName(name);
    // Location
    String location=DOMParsingTools.getStringAttribute(attrs,ServerXMLConstants.SERVER_LOCATION_ATTR,"");
    server.setLocation(location);
    // Address
    String addressStr=DOMParsingTools.getStringAttribute(attrs,ServerXMLConstants.SERVER_ADDRESS_ATTR,null);
    InetAddress address=null;
    try
    {
      address=InetAddress.getByName(addressStr);
    }
    catch(IOException ioe)
    {
      LOGGER.error("Cannot build server address", ioe);
    }
    server.setAddress(address);
    return server;
  }
}
