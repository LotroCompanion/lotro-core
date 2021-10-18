package delta.games.lotro.lore.items.legendary2.global.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.legendary2.global.LegendaryData2;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes data of the legendary system (reloaded) to XML files.
 * @author DAM
 */
public class LegendaryData2XMLWriter
{
  /**
   * Write data of the legendary system (reloaded) to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final LegendaryData2 data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLegendaryData(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeLegendaryData(TransformerHandler hd, LegendaryData2 data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Character level to item level progression
    Progression progression=data.getCharacterLevel2ItemLevelProgression();
    int progressionID=progression.getIdentifier();
    attrs.addAttribute("","",LegendaryData2XMLConstants.CHARACTER_LEVEL_TO_ITEM_LEVEL_PROGRESSION_ID_ATTR,XmlWriter.CDATA,String.valueOf(progressionID));
    hd.startElement("","",LegendaryData2XMLConstants.LEGENDARY2_TAG,attrs);
    hd.endElement("","",LegendaryData2XMLConstants.LEGENDARY2_TAG);
  }
}
