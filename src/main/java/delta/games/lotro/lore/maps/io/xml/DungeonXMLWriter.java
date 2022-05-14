package delta.games.lotro.lore.maps.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.lore.maps.Dungeon;

/**
 * Writes dungeons to XML files.
 * @author DAM
 */
public class DungeonXMLWriter
{
  /**
   * Write a file with dungeons.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeDungeonsFile(File toFile, List<Dungeon> data)
  {
    DungeonXMLWriter writer=new DungeonXMLWriter();
    boolean ok=writer.writeDungeons(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write dungeons to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeDungeons(File outFile, final List<Dungeon> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeDungeons(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeDungeons(TransformerHandler hd, List<Dungeon> data) throws SAXException
  {
    hd.startElement("","",DungeonXMLConstants.DUNGEONS_TAG,new AttributesImpl());
    // Maps
    for(Dungeon dungeon : data)
    {
      writeDungeon(hd,dungeon);
    }
    hd.endElement("","",DungeonXMLConstants.DUNGEONS_TAG);
  }

  private void writeDungeon(TransformerHandler hd, Dungeon dungeon) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=dungeon.getIdentifier();
    attrs.addAttribute("","",DungeonXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=dungeon.getName();
    attrs.addAttribute("","",DungeonXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Basemap identifier
    int basemapId=dungeon.getBasemapId();
    attrs.addAttribute("","",DungeonXMLConstants.BASEMAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(basemapId));

    hd.startElement("","",DungeonXMLConstants.DUNGEON_TAG,attrs);
    // Position
    PositionXMLWriter.writePosition(hd,dungeon.getMapPosition());
    hd.endElement("","",DungeonXMLConstants.DUNGEON_TAG);
  }
}
