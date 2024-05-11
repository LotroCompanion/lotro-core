package delta.games.lotro.character.status.summary.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.summary.AchievementsSummary;

/**
 * Writes an achievements summary to an XML file.
 * @author DAM
 */
public class AchievementsSummaryXMLWriter
{
  /**
   * Write an achievements summary to an XML file.
   * @param outFile Output file.
   * @param summary Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final AchievementsSummary summary, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeAchievementsSummary(hd,summary);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeAchievementsSummary(TransformerHandler hd, AchievementsSummary summary) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Deeds
    Integer deeds=summary.getDeedsCount();
    if (deeds!=null)
    {
      attrs.addAttribute("","",AchievementsSummaryXMLConstants.DEEDS_ATTR,XmlWriter.CDATA,String.valueOf(deeds));
    }
    // Quests
    Integer quests=summary.getQuestsCount();
    if (quests!=null)
    {
      attrs.addAttribute("","",AchievementsSummaryXMLConstants.QUESTS_ATTR,XmlWriter.CDATA,String.valueOf(quests));
    }
    // Titles
    Integer titles=summary.getTitlesCount();
    if (titles!=null)
    {
      attrs.addAttribute("","",AchievementsSummaryXMLConstants.TITLES_ATTR,XmlWriter.CDATA,String.valueOf(titles));
    }
    hd.startElement("","",AchievementsSummaryXMLConstants.ACHIEVEMENTS_SUMMARY_TAG,attrs);
    hd.endElement("","",AchievementsSummaryXMLConstants.ACHIEVEMENTS_SUMMARY_TAG);
  }
}
