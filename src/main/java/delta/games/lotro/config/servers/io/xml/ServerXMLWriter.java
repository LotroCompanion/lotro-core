package delta.games.lotro.config.servers.io.xml;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.config.servers.ServerDescription;

/**
 * Writes LOTRO server descriptions to XML files.
 * @author DAM
 */
public class ServerXMLWriter
{
  /**
   * Write a file with servers.
   * @param toFile Output file.
   * @param servers Servers to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeServersFile(File toFile, List<ServerDescription> servers)
  {
    ServerXMLWriter writer=new ServerXMLWriter();
    boolean ok=writer.writeServers(toFile,servers,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write servers to a XML file.
   * @param outFile Output file.
   * @param servers Servers to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeServers(File outFile, final List<ServerDescription> servers, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws SAXException
      {
        writeServers(hd,servers);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeServers(TransformerHandler hd, List<ServerDescription> servers) throws SAXException
  {
    hd.startElement("","",ServerXMLConstants.SERVERS_TAG,new AttributesImpl());
    for(ServerDescription server : servers)
    {
      writeServer(hd,server);
    }
    hd.endElement("","",ServerXMLConstants.SERVERS_TAG);
  }

  private void writeServer(TransformerHandler hd, ServerDescription server) throws SAXException
  {
    AttributesImpl serverAttrs=new AttributesImpl();

    // Name
    String name=server.getName();
    serverAttrs.addAttribute("","",ServerXMLConstants.SERVER_NAME_ATTR,XmlWriter.CDATA,name);
    // Location
    String location=server.getLocation();
    serverAttrs.addAttribute("","",ServerXMLConstants.SERVER_LOCATION_ATTR,XmlWriter.CDATA,location);
    // Address
    InetAddress address=server.getAddress();
    if (address!=null)
    {
      serverAttrs.addAttribute("","",ServerXMLConstants.SERVER_ADDRESS_ATTR,XmlWriter.CDATA,address.getHostAddress());
    }
    hd.startElement("","",ServerXMLConstants.SERVER_TAG,serverAttrs);
    hd.endElement("","",ServerXMLConstants.SERVER_TAG);
  }
}
