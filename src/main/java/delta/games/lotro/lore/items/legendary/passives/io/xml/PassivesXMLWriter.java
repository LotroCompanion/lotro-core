package delta.games.lotro.lore.items.legendary.passives.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.legendary.passives.Passive;

/**
 * Writes passives to XML documents.
 * @author DAM
 */
public class PassivesXMLWriter
{
  /**
   * Write some passives to a XML file.
   * @param toFile File to write to.
   * @param passives Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Passive> passives)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",PassivesXMLConstants.PASSIVES_TAG,new AttributesImpl());
        for(Passive passive : passives)
        {
          writePassive(hd,passive);
        }
        hd.endElement("","",PassivesXMLConstants.PASSIVES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a passive to a XML document.
   * @param hd Output.
   * @param passive Data to write.
   * @throws SAXException If an error occurs.
   */
  private static void writePassive(TransformerHandler hd, Passive passive) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=passive.getIdentifier();
    attrs.addAttribute("","",PassivesXMLConstants.PASSIVE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",PassivesXMLConstants.PASSIVE_TAG,attrs);
    // Stats
    StatsProvider statsProvider=passive.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,statsProvider);
    }
    hd.endElement("","",PassivesXMLConstants.PASSIVE_TAG);
  }
}
