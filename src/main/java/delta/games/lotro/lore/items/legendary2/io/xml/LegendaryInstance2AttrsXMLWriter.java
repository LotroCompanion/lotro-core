package delta.games.lotro.lore.items.legendary2.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.items.legendary2.LegendaryInstanceAttrs2;
import delta.games.lotro.lore.items.legendary2.SocketEntryInstance;
import delta.games.lotro.lore.items.legendary2.SocketsSetup;
import delta.games.lotro.lore.items.legendary2.SocketsSetupInstance;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Writes legendary instance attributes (reloaded) to XML documents.
 * @author DAM
 */
public class LegendaryInstance2AttrsXMLWriter
{
  /**
   * Write legendary instance attrs (reloaded) to the given XML stream.
   * @param hd XML output stream.
   * @param legendaryData Legendary data to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, LegendaryInstanceAttrs2 legendaryData) throws Exception
  {
    AttributesImpl legendaryAttrs=new AttributesImpl();
    // Name
    String legendaryName=legendaryData.getLegendaryName();
    if (legendaryName.length()>0)
    {
      legendaryAttrs.addAttribute("","",LegendaryInstance2AttrsXMLConstants.LEGENDARY_NAME_ATTR,XmlWriter.CDATA,legendaryName);
    }
    hd.startElement("","",LegendaryInstance2AttrsXMLConstants.LEGENDARY_TAG,legendaryAttrs);
    // Traceries
    SocketsSetupInstance socketsSetupInstance=legendaryData.getSocketsSetup();
    SocketsSetup socketsSetup=socketsSetupInstance.getSetupTemplate();
    int nbSockets=socketsSetup.getSocketsCount();
    for(int i=0;i<nbSockets;i++)
    {
      SocketEntryInstance entry=socketsSetupInstance.getEntry(i);
      Tracery tracery=entry.getTracery();
      if (tracery!=null)
      {
        AttributesImpl socketAttrs=new AttributesImpl();
        // Index
        socketAttrs.addAttribute("","",LegendaryInstance2AttrsXMLConstants.SOCKET_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(i));
        // Tracery ID
        int traceryID=tracery.getIdentifier();
        socketAttrs.addAttribute("","",LegendaryInstance2AttrsXMLConstants.SOCKET_TRACERY_ID_ATTR,XmlWriter.CDATA,String.valueOf(traceryID));
        // Tracery name
        String traceryName=tracery.getName();
        socketAttrs.addAttribute("","",LegendaryInstance2AttrsXMLConstants.SOCKET_TRACERY_NAME_ATTR,XmlWriter.CDATA,traceryName);
        // Item level
        int itemLevel=entry.getItemLevel();
        socketAttrs.addAttribute("","",LegendaryInstance2AttrsXMLConstants.SOCKET_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(itemLevel));
        hd.startElement("","",LegendaryInstance2AttrsXMLConstants.SOCKET_TAG,socketAttrs);
        hd.endElement("","",LegendaryInstance2AttrsXMLConstants.SOCKET_TAG);
      }
    }
    hd.endElement("","",LegendaryInstance2AttrsXMLConstants.LEGENDARY_TAG);
  }
}
