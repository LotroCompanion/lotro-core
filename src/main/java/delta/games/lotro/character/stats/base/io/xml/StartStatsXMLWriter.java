package delta.games.lotro.character.stats.base.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.StartStatsManager;

/**
 * Writes start stats to XML files.
 * @author DAM
 */
public class StartStatsXMLWriter
{
  /**
   * Write a start stats manager to a XML file.
   * @param toFile File to write to.
   * @param statsManager Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final StartStatsManager statsManager)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",StartStatsXMLConstants.START_STATS_TAG,new AttributesImpl());
        for(ClassDescription characterClass : statsManager.getClasses())
        {
          List<Integer> levels=statsManager.getLevels(characterClass);
          for(Integer level : levels)
          {
            BasicStatsSet stats=statsManager.getStats(characterClass,level.intValue());
            AttributesImpl attrs=new AttributesImpl();
            attrs.addAttribute("","",StartStatsXMLConstants.STATS_CLASS_ATTR,CDATA,characterClass.getKey());
            attrs.addAttribute("","",StartStatsXMLConstants.STATS_LEVEL_ATTR,CDATA,level.toString());
            hd.startElement("","",StartStatsXMLConstants.STATS_TAG,attrs);
            BasicStatsSetXMLWriter.writeStats(hd,stats);
            hd.endElement("","",StartStatsXMLConstants.STATS_TAG);
          }
        }
        hd.endElement("","",StartStatsXMLConstants.START_STATS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }
}
