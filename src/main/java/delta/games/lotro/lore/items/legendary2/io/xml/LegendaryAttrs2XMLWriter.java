package delta.games.lotro.lore.items.legendary2.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.legendary2.Legendary2;
import delta.games.lotro.lore.items.legendary2.LegendaryAttrs2;
import delta.games.lotro.lore.items.legendary2.SocketEntry;
import delta.games.lotro.lore.items.legendary2.SocketsSetup;

/**
 * Writes legendary attributes (reloaded) to XML documents.
 * @author DAM
 */
public class LegendaryAttrs2XMLWriter
{
  /**
   * Write some legendary data to a XML file.
   * @param toFile File to write to.
   * @param legendaryItems Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Legendary2> legendaryItems)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",LegendaryAttrs2XMLConstants.LEGENDARY_DATA_TAG,new AttributesImpl());
        for(Legendary2 legendary2 : legendaryItems)
        {
          LegendaryAttrs2 attrs2=legendary2.getLegendaryAttrs();
          write(hd,attrs2);
        }
        hd.endElement("","",LegendaryAttrs2XMLConstants.LEGENDARY_DATA_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write legendary instance attributes to the given XML stream.
   * @param hd XML output stream.
   * @param legendaryData Legendary data to write.
   * @throws SAXException If an error occurs.
   */
  private static void write(TransformerHandler hd, LegendaryAttrs2 legendaryData) throws SAXException
  {
    AttributesImpl legendaryAttrs=new AttributesImpl();
    // Item ID
    SocketsSetup socketsSetup=legendaryData.getSockets();
    int itemID=socketsSetup.getIdentifier();
    legendaryAttrs.addAttribute("","",LegendaryAttrs2XMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
    hd.startElement("","",LegendaryAttrs2XMLConstants.SOCKETS_SETUP_TAG,legendaryAttrs);
    // Sockets
    int nbSockets=socketsSetup.getSocketsCount();
    for(int i=0;i<nbSockets;i++)
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      SocketEntry entry=socketsSetup.getSocket(i);
      // Type
      SocketType type=entry.getType();
      int typeCode=type.getCode();
      entryAttrs.addAttribute("","",LegendaryAttrs2XMLConstants.SOCKET_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(typeCode));
      // Unlock level
      int unlockLevel=entry.getUnlockItemLevel();
      entryAttrs.addAttribute("","",LegendaryAttrs2XMLConstants.SOCKET_UNLOCK_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(unlockLevel));
      hd.startElement("","",LegendaryAttrs2XMLConstants.SOCKET_SETUP_TAG,entryAttrs);
      hd.endElement("","",LegendaryAttrs2XMLConstants.SOCKET_SETUP_TAG);
    }
    hd.endElement("","",LegendaryAttrs2XMLConstants.SOCKETS_SETUP_TAG);
  }
}
