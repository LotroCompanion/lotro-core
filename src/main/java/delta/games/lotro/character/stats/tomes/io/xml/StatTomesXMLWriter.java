package delta.games.lotro.character.stats.tomes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.character.stats.tomes.StatTome;
import delta.games.lotro.character.stats.tomes.StatTomesManager;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Writes stat tomes to XML files.
 * @author DAM
 */
public class StatTomesXMLWriter
{
  /**
   * Write a stat tomes manager to a XML file.
   * @param toFile File to write to.
   * @param statTomesMgr Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final StatTomesManager statTomesMgr)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",StatTomesXMLConstants.STAT_TOMES_TAG,new AttributesImpl());
        writeStatTomesManager(hd,statTomesMgr);
        hd.endElement("","",StatTomesXMLConstants.STAT_TOMES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeStatTomesManager(TransformerHandler hd, StatTomesManager statTomesMgr) throws Exception
  {
    List<StatDescription> stats=statTomesMgr.getStats();
    for(StatDescription stat : stats)
    {
      AttributesImpl attrs=new AttributesImpl();
      // Key
      String key=stat.getPersistenceKey();
      attrs.addAttribute("","",StatTomesXMLConstants.STAT_IDENTIFIER_ATTR,XmlWriter.CDATA,key);
      hd.startElement("","",StatTomesXMLConstants.STAT_TAG,attrs);
      // Tomes
      int nbRanks=statTomesMgr.getNbOfRanks(stat);
      for(int i=1;i<=nbRanks;i++)
      {
        StatTome tome=statTomesMgr.getStatTome(stat,i);
        if (tome!=null)
        {
          AttributesImpl tomeAttrs=new AttributesImpl();
          // Rank
          int rank=tome.getRank();
          tomeAttrs.addAttribute("","",StatTomesXMLConstants.TOME_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
          // Trait identifier
          int traitId=tome.getTraitId();
          tomeAttrs.addAttribute("","",StatTomesXMLConstants.TOME_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
          hd.startElement("","",StatTomesXMLConstants.TOME_TAG,tomeAttrs);
          // Stats
          BasicStatsSet tomeStats=tome.getStats();
          BasicStatsSetXMLWriter.write(hd,BasicStatsSetXMLConstants.STATS_TAG,tomeStats);
          hd.endElement("","",StatTomesXMLConstants.TOME_TAG);
        }
      }
      hd.endElement("","",StatTomesXMLConstants.STAT_TAG);
    }
  }
}
