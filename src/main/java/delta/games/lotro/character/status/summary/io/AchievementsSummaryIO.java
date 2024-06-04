package delta.games.lotro.character.status.summary.io;

import java.io.File;
import java.util.List;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.character.status.achievables.io.DeedsStatusIo;
import delta.games.lotro.character.status.achievables.io.QuestsStatusIo;
import delta.games.lotro.character.status.summary.AchievementsSummary;
import delta.games.lotro.character.status.summary.io.xml.AchievementsSummaryXMLParser;
import delta.games.lotro.character.status.summary.io.xml.AchievementsSummaryXMLWriter;
import delta.games.lotro.character.status.titles.TitlesStatusManager;
import delta.games.lotro.character.status.titles.io.TitlesStatusIo;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.AchievablesUtils;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.utils.events.EventsManager;

/**
 * I/O methods for storage summaries.
 * @author DAM
 */
public class AchievementsSummaryIO
{
  /**
   * Load achievements summary.
   * @param character Parent character.
   * @return the loaded summary.
   */
  public static AchievementsSummary loadAchievementsSummary(CharacterFile character)
  {
    AchievementsSummary ret=loadAchievementsSummaryIfExists(character);
    if (ret==null)
    {
      ret=buildAchievementsSummaryFromDetails(character);
      save(character,ret);
    }
    return ret;
  }

  /**
   * Load achievements summary.
   * @param character Parent character.
   * @return the loaded summary.
   */
  public static AchievementsSummary loadAchievementsSummaryIfExists(CharacterFile character)
  {
    File fromFile=getAchievementsSummaryFile(character);
    AchievementsSummary ret=null;
    if (fromFile.exists())
    {
      AchievementsSummaryXMLParser parser=new AchievementsSummaryXMLParser();
      ret=parser.parseXML(fromFile);
    }
    return ret;
  }

  /**
   * Save the achievements summary for a character.
   * @param character Targeted character.
   * @param summary Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, AchievementsSummary summary)
  {
    File toFile=getAchievementsSummaryFile(character);
    AchievementsSummaryXMLWriter writer=new AchievementsSummaryXMLWriter();
    boolean ok=writer.write(toFile,summary,EncodingNames.UTF_8);
    CharacterEvent event=new CharacterEvent(CharacterEventType.CHARACTER_SUMMARY_UPDATED,character,null);
    EventsManager.invokeEvent(event);
    return ok;
  }

  private static File getAchievementsSummaryFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File ret=new File(rootDir,"achievementsSummary.xml");
    return ret;
  }

  /**
   * Build an achievements summary from available status details.
   * @param character Targeted character.
   * @return the loaded data.
   */
  public static AchievementsSummary buildAchievementsSummaryFromDetails(CharacterFile character)
  {
    AchievementsSummary ret=new AchievementsSummary();
    // Deeds
    AchievablesStatusManager deedsStatusMgr=DeedsStatusIo.loadIfExists(character);
    if (deedsStatusMgr!=null)
    {
      ret=updateAchievementsSummaryForDeeds(character,deedsStatusMgr);
    }
    // Quests
    AchievablesStatusManager questsStatusMgr=QuestsStatusIo.loadIfExists(character);
    if (questsStatusMgr!=null)
    {
      ret=updateAchievementsSummaryForQuests(character,questsStatusMgr);
    }
    // Titles
    TitlesStatusManager titlesStatusMgr=TitlesStatusIo.loadIfExists(character);
    if (titlesStatusMgr!=null)
    {
      ret=updateAchievementsSummaryForTitles(character,titlesStatusMgr);
    }
    return ret;
  }

  /**
   * Update the achievements summary for deeds.
   * @param character Targeted character.
   * @param deedsStatusMgr Deeds status manager.
   * @return the updated summary. 
   */
  public static AchievementsSummary updateAchievementsSummaryForDeeds(CharacterFile character, AchievablesStatusManager deedsStatusMgr)
  {
    AchievementsSummary ret=loadAchievementsSummaryIfExists(character);
    if (ret==null)
    {
      ret=new AchievementsSummary();
    }
    List<DeedDescription> deeds=AchievablesUtils.getDeeds(character);
    int deedsCount=deedsStatusMgr.getTotalCompletionsCount(deeds);
    ret.setDeedsCount(Integer.valueOf(deedsCount));
    save(character,ret);
    return ret;
  }

  /**
   * Update the achievements summary for quests.
   * @param character Targeted character.
   * @param questsStatusMgr Quests status manager.
   * @return the updated summary. 
   */
  public static AchievementsSummary updateAchievementsSummaryForQuests(CharacterFile character, AchievablesStatusManager questsStatusMgr)
  {
    AchievementsSummary ret=loadAchievementsSummaryIfExists(character);
    if (ret==null)
    {
      ret=new AchievementsSummary();
    }
    List<QuestDescription> quests=AchievablesUtils.getQuests(character);
    int questsCount=questsStatusMgr.getTotalCompletionsCount(quests);
    ret.setQuestsCount(Integer.valueOf(questsCount));
    save(character,ret);
    return ret;
  }

  /**
   * Update the achievements summary for titles.
   * @param character Targeted character.
   * @param titlesStatusMgr Titles status manager.
   * @return the updated summary. 
   */
  public static AchievementsSummary updateAchievementsSummaryForTitles(CharacterFile character, TitlesStatusManager titlesStatusMgr)
  {
    AchievementsSummary ret=loadAchievementsSummaryIfExists(character);
    if (ret==null)
    {
      ret=new AchievementsSummary();
    }
    int titles=titlesStatusMgr.getTitlesCount();
    ret.setTitlesCount(Integer.valueOf(titles));
    save(character,ret);
    return ret;
  }
}
