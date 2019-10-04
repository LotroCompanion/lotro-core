package delta.games.lotro.character.stats.base;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.StartStatsManagerIO;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for base character stats.
 * @author DAM
 */
public class BaseStatsManager
{
  private StartStatsManager _startStatsManager;
  private BasicStatsSet _toAdd;

  /**
   * Constructor.
   */
  public BaseStatsManager()
  {
    _startStatsManager=StartStatsManagerIO.load();
    _toAdd=new BasicStatsSet();
    _toAdd.setStat(WellKnownStat.PHYSICAL_MASTERY,1);
    _toAdd.setStat(WellKnownStat.TACTICAL_MASTERY,1);
    _toAdd.setStat(WellKnownStat.BLOCK,new FixedDecimalsInteger(1.5f));
  }

  /**
   * Get base stats for a given class/race/level.
   * @param cClass Character class.
   * @param race Race.
   * @param level Level (starting at 1).
   * @return A set of stats.
   */
  public BasicStatsSet getBaseStats(CharacterClass cClass, Race race, int level)
  {
    BasicStatsSet classSet=_startStatsManager.getStats(cClass,level);
    BasicStatsSet raceSet=getRaceTraitsContrib(race,level);
    BasicStatsSet classTraitsSet=getClassTraitsContrib(cClass,level);
    BasicStatsSet global=new BasicStatsSet();
    global.addStats(classSet);
    global.addStats(raceSet);
    global.addStats(classTraitsSet);
    global.addStats(_toAdd);
    return global;
  }

  private BasicStatsSet getRaceTraitsContrib(Race race, int level)
  {
    RacesManager racesManager=RacesManager.getInstance();
    RaceDescription description=racesManager.getRaceDescription(race);
    BasicStatsSet stats=new BasicStatsSet();
    List<TraitDescription> traits=description.getTraitsForLevel(level);
    for(TraitDescription trait : traits)
    {
      StatsProvider provider=trait.getStatsProvider();
      BasicStatsSet statsForTrait=provider.getStats(1,level,true);
      int nbStats=statsForTrait.getStatsCount();
      if (nbStats>0)
      {
        stats.addStats(statsForTrait);
      }
    }
    return stats;
  }

  private BasicStatsSet getClassTraitsContrib(CharacterClass cClass, int level)
  {
    ClassesManager classesManager=ClassesManager.getInstance();
    ClassDescription description=classesManager.getClassDescription(cClass);
    BasicStatsSet stats=new BasicStatsSet();
    List<TraitDescription> traits=description.getTraitsForLevel(level);
    for(TraitDescription trait : traits)
    {
      if ("Audacity".equals(trait.getName())) continue;
      StatsProvider provider=trait.getStatsProvider();
      BasicStatsSet statsForTrait=provider.getStats(1,level,true);
      int nbStats=statsForTrait.getStatsCount();
      if (nbStats>0)
      {
        stats.addStats(statsForTrait);
      }
    }
    return stats;
  }
}
