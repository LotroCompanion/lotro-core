package delta.games.lotro.character.level.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.level.LevelHistory;

/**
 * Writes level history of LOTRO characters to XML files.
 * @author DAM
 */
public class LevelHistoryXMLWriter
{
  /**
   * Write the level history of a character to a XML file.
   * @param outFile Output file.
   * @param history Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final LevelHistory history, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,history);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, LevelHistory history) throws Exception
  {
    AttributesImpl characterAttrs=new AttributesImpl();
    String name=history.getName();
    if (name!=null)
    {
      characterAttrs.addAttribute("","",LevelHistoryXMLConstants.LEVEL_HISTORY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",LevelHistoryXMLConstants.LEVEL_HISTORY_TAG,characterAttrs);
    int[] levels=history.getLevels();
    for(int i=0;i<levels.length;i++)
    {
      int level=levels[i];
      Long date=history.getDate(level);
      AttributesImpl levelAttrs=new AttributesImpl();
      levelAttrs.addAttribute("","",LevelHistoryXMLConstants.LEVEL_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(level));
      levelAttrs.addAttribute("","",LevelHistoryXMLConstants.LEVEL_DATE_ATTR,XmlWriter.CDATA,String.valueOf(date.longValue()));
      hd.startElement("","",LevelHistoryXMLConstants.LEVEL_TAG,levelAttrs);
      hd.endElement("","",LevelHistoryXMLConstants.LEVEL_TAG);
    }
    hd.endElement("","",LevelHistoryXMLConstants.LEVEL_HISTORY_TAG);
  }
}
