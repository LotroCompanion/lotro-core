package delta.games.lotro.lore.mood.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.mood.MoodEntry;

/**
 * Writes mood data to XML files.
 * @author DAM
 */
public class MoodXMLWriter
{
  /**
   * Write a file with mood data.
   * @param toFile Output file.
   * @param moods Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMoodsFile(File toFile, List<MoodEntry> moods)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMoods(hd,moods);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeMoods(TransformerHandler hd, List<MoodEntry> moods) throws SAXException
  {
    hd.startElement("","",MoodXMLConstants.MOODS_TAG,new AttributesImpl());
    for(MoodEntry mood : moods)
    {
      AttributesImpl attrs=new AttributesImpl();
      // Level
      int level=mood.getLevel();
      attrs.addAttribute("","",MoodXMLConstants.MOOD_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
      // Morale modifier
      float moraleModifier=mood.getMoraleModifier();
      attrs.addAttribute("","",MoodXMLConstants.MOOD_MORALE_MODIFIER,XmlWriter.CDATA,String.valueOf(moraleModifier));
      hd.startElement("","",MoodXMLConstants.MOOD_TAG,attrs);
      hd.endElement("","",MoodXMLConstants.MOOD_TAG);
    }
    hd.endElement("","",MoodXMLConstants.MOODS_TAG);
  }
}
