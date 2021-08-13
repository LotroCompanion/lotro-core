package delta.games.lotro.character.status.skirmishes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.skirmishes.SingleSkirmishStats;
import delta.games.lotro.character.status.skirmishes.SkirmishLevel;
import delta.games.lotro.character.status.skirmishes.SkirmishStats;
import delta.games.lotro.character.status.skirmishes.SkirmishStatsManager;
import delta.games.lotro.lore.instances.SkirmishGroupSize;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Writes skimirsh stats to an XML file.
 * @author DAM
 */
public class SkirmishStatsXMLWriter
{
  /**
   * Writes skirmish stats to an XML file.
   * @param outFile Output file.
   * @param statsMgr Stats to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final SkirmishStatsManager statsMgr, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStats(hd,statsMgr);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write stats to the given XML stream.
   * @param hd XML output stream.
   * @param statsMgr Stats to write.
   * @throws Exception If an error occurs.
   */
  private void writeStats(TransformerHandler hd, SkirmishStatsManager statsMgr) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",SkirmishStatsXMLConstants.MAIN_TAG,attrs);
    List<SingleSkirmishStats> stats=statsMgr.getStats();
    for(SingleSkirmishStats skirmishStats : stats)
    {
      writeSingleSkirmishStats(hd,skirmishStats);
    }
    hd.endElement("","",SkirmishStatsXMLConstants.MAIN_TAG);
  }

  /**
   * Write stats for a single skirmish to the given XML stream.
   * @param hd XML output stream.
   * @param stats Stats to write.
   * @throws Exception If an error occurs.
   */
  private void writeSingleSkirmishStats(TransformerHandler hd, SingleSkirmishStats stats) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    SkirmishPrivateEncounter skirmish=stats.getSkirmish();
    // ID
    int skirmishID=skirmish.getIdentifier();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.SKIRMISH_ID_ATTR,XmlWriter.CDATA,String.valueOf(skirmishID));
    // Name
    String skirmishName=skirmish.getName();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.SKIRMISH_NAME_ATTR,XmlWriter.CDATA,skirmishName);
    hd.startElement("","",SkirmishStatsXMLConstants.SKIRMISH_TAG,attrs);
    for(SkirmishGroupSize size : SkirmishGroupSize.values())
    {
      for(SkirmishLevel level : SkirmishLevel.values())
      {
        SkirmishStats detailedStats=stats.getStats(size,level);
        if (detailedStats!=null)
        {
          AttributesImpl skirmishAttrs=new AttributesImpl();
          // Group size
          skirmishAttrs.addAttribute("","",SkirmishStatsXMLConstants.GROUP_SIZE_ATTR,XmlWriter.CDATA,size.name());
          // Level
          skirmishAttrs.addAttribute("","",SkirmishStatsXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,level.name());
          writeSkirmishStats(hd,detailedStats,skirmishAttrs);
          hd.startElement("","",SkirmishStatsXMLConstants.SKIRMISH_STATS_TAG,skirmishAttrs);
          hd.endElement("","",SkirmishStatsXMLConstants.SKIRMISH_STATS_TAG);
        }
      }
    }
    hd.endElement("","",SkirmishStatsXMLConstants.SKIRMISH_TAG);
  }

  /**
   * Write skirmish stats to the given XML stream.
   * @param stats Stats to write.
   * @param hd XML output stream.
   * @param attrs Storage for attributes to write.
   * @throws Exception If an error occurs.
   */
  private void writeSkirmishStats(TransformerHandler hd, SkirmishStats stats, AttributesImpl attrs) throws Exception
  {
    // Monster kills
    int monsterKills=stats.getMonsterKills();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.MONSTER_KILLS_ATTR,XmlWriter.CDATA,String.valueOf(monsterKills));
    // Lieutenant kills
    int lieutenantKills=stats.getLieutenantKills();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.LIEUTENANT_KILLS_ATTR,XmlWriter.CDATA,String.valueOf(lieutenantKills));
    // Control points taken
    int controlPointsTaken=stats.getControlPointsTaken();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.CONTROL_POINTS_TAKEN_ATTR,XmlWriter.CDATA,String.valueOf(controlPointsTaken));
    // Encounters completed
    int encountersCompleted=stats.getEncountersCompleted();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.ENCOUNTERS_COMPLETED_ATTR,XmlWriter.CDATA,String.valueOf(encountersCompleted));
    // Play time
    float playTime=stats.getPlayTime();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.PLAY_TIME_ATTR,XmlWriter.CDATA,String.valueOf(playTime));
    // Skirmishes completed
    int skirmishesCompleted=stats.getSkirmishesCompleted();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.SKIRMISHES_COMPLETED_ATTR,XmlWriter.CDATA,String.valueOf(skirmishesCompleted));
    // Skirmishes attempted
    int skirmishesAttempted=stats.getSkirmishesAttempted();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.SKIRMISHES_ATTEMPTED_ATTR,XmlWriter.CDATA,String.valueOf(skirmishesAttempted));
    // Best time
    float bestTime=stats.getBestTime();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.BEST_TIME_ATTR,XmlWriter.CDATA,String.valueOf(bestTime));
    // Total marks earned
    int totalMarksEarned=stats.getTotalMarksEarned();
    attrs.addAttribute("","",SkirmishStatsXMLConstants.TOTAL_MARKS_EARNED_ATTR,XmlWriter.CDATA,String.valueOf(totalMarksEarned));
  }
}
