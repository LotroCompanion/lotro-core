package delta.games.lotro.character.status.skirmishes.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.skirmishes.SingleSkirmishStats;
import delta.games.lotro.character.status.skirmishes.SkirmishLevel;
import delta.games.lotro.character.status.skirmishes.SkirmishStats;
import delta.games.lotro.character.status.skirmishes.SkirmishStatsManager;
import delta.games.lotro.common.enums.GroupSize;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Parser for the skirmish statistics stored in XML.
 * @author DAM
 */
public class SkirmishStatsXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkirmishStatsXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed stats or <code>null</code>.
   */
  public SkirmishStatsManager parseXML(File source)
  {
    SkirmishStatsManager stats=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      stats=parseStats(root);
    }
    return stats;
  }

  private SkirmishStatsManager parseStats(Element root)
  {
    SkirmishStatsManager stats=new SkirmishStatsManager();
    List<Element> skirmishTags=DOMParsingTools.getChildTagsByName(root,SkirmishStatsXMLConstants.SKIRMISH_TAG,false);
    for(Element skirmishTag : skirmishTags)
    {
      parseSkirmishStats(stats,skirmishTag);
    }
    return stats;
  }

  private void parseSkirmishStats(SkirmishStatsManager stats, Element rootSkirmishTag)
  {
    NamedNodeMap attrs=rootSkirmishTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.SKIRMISH_ID_ATTR,0);
    PrivateEncountersManager peMgr=PrivateEncountersManager.getInstance();
    PrivateEncounter pe=peMgr.getPrivateEncounterById(id);
    if (!(pe instanceof SkirmishPrivateEncounter))
    {
      return;
    }
    SkirmishPrivateEncounter skirmish=(SkirmishPrivateEncounter)pe;
    SingleSkirmishStats skirmishStats=new SingleSkirmishStats(skirmish);
    List<Element> skirmishTags=DOMParsingTools.getChildTagsByName(rootSkirmishTag,SkirmishStatsXMLConstants.SKIRMISH_STATS_TAG,false);
    for(Element skirmishTag : skirmishTags)
    {
      parseSkirmishStats(skirmishStats,skirmishTag);
    }
    stats.add(skirmishStats);
  }

  private void parseSkirmishStats(SingleSkirmishStats stats, Element skirmishTag)
  {
    NamedNodeMap attrs=skirmishTag.getAttributes();
    // Group size
    String groupSizeKey=DOMParsingTools.getStringAttribute(attrs,SkirmishStatsXMLConstants.GROUP_SIZE_ATTR,null);
    LotroEnum<GroupSize> groupSizesMgr=LotroEnumsRegistry.getInstance().get(GroupSize.class);
    GroupSize groupSize=groupSizesMgr.getByKey(groupSizeKey);
    if (groupSize==null)
    {
      LOGGER.warn("Unmanaged group size: {}",groupSizeKey);
      return;
    }
    // Level
    String levelStr=DOMParsingTools.getStringAttribute(attrs,SkirmishStatsXMLConstants.LEVEL_ATTR,null);
    SkirmishLevel level=parseLevel(levelStr);
    if (level==null)
    {
      LOGGER.warn("Unmanaged level: {}",levelStr);
      return;
    }

    SkirmishStats ss=new SkirmishStats();
    // Monster kills
    int monsterKills=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.MONSTER_KILLS_ATTR,0);
    ss.setMonsterKills(monsterKills);
    // Lieutenant kills
    int lieutenantKills=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.LIEUTENANT_KILLS_ATTR,0);
    ss.setLieutenantKills(lieutenantKills);
    // Boss kills
    int bossKills=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.BOSS_KILLS_ATTR,0);
    ss.setBossKills(bossKills);
    // Boss resets
    int bossResets=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.BOSS_RESETS_ATTR,0);
    ss.setBossResets(bossResets);
    // Defenders lost
    int defendersLost=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.DEFENDER_LOST_ATTR,0);
    ss.setDefendersLost(defendersLost);
    // Defenders saved
    int defendersSaved=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.DEFENDER_SAVED_ATTR,0);
    ss.setDefendersSaved(defendersSaved);
    // Soldier deaths
    int soldierDeaths=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.SOLDIER_DEATHS_ATTR,0);
    ss.setSoldiersDeaths(soldierDeaths);
    // Control points taken
    int cpTaken=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.CONTROL_POINTS_TAKEN_ATTR,0);
    ss.setControlPointsTaken(cpTaken);
    // Encounters completed
    int encountersCompleted=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.ENCOUNTERS_COMPLETED_ATTR,0);
    ss.setEncountersCompleted(encountersCompleted);
    // Play time
    float playTime=DOMParsingTools.getFloatAttribute(attrs,SkirmishStatsXMLConstants.PLAY_TIME_ATTR,0.0f);
    ss.setPlayTime(playTime);
    // Skirmishes completed
    int skirmishesCompleted=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.SKIRMISHES_COMPLETED_ATTR,0);
    ss.setSkirmishesCompleted(skirmishesCompleted);
    // Skirmishes attempted
    int skirmishesAttempted=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.SKIRMISHES_ATTEMPTED_ATTR,0);
    ss.setSkirmishesAttempted(skirmishesAttempted);
    // Best time
    float bestTime=DOMParsingTools.getFloatAttribute(attrs,SkirmishStatsXMLConstants.BEST_TIME_ATTR,0.0f);
    ss.setBestTime(bestTime);
    // Marks earned
    int totalMarksEarned=DOMParsingTools.getIntAttribute(attrs,SkirmishStatsXMLConstants.TOTAL_MARKS_EARNED_ATTR,0);
    ss.setTotalMarksEarned(totalMarksEarned);

    stats.setStats(groupSize,level,ss);
  }

  private SkirmishLevel parseLevel(String levelStr)
  {
    SkirmishLevel ret=null;
    if (levelStr!=null)
    {
      try
      {
        ret=SkirmishLevel.valueOf(levelStr);
      }
      catch(Exception e)
      {
        // Ignored
      }
    }
    return ret;
  }
}
