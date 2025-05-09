package delta.games.lotro.character.stats.base;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.StartStatsManagerIO;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;

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
    _toAdd.setStat(WellKnownStat.PARRY,3);
    _toAdd.setStat(WellKnownStat.EVADE,1);
  }

  /**
   * Get base stats for a given class/race/level.
   * @param characterClass Character class.
   * @param race Race.
   * @param level Level (starting at 1).
   * @return A list of stats contributions.
   */
  public List<StatsContribution> getBaseStats(ClassDescription characterClass, RaceDescription race, int level)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    BasicStatsSet baseStats=_startStatsManager.getStats(characterClass,level);
    BasicStatsSet bodyStats=new BasicStatsSet(baseStats);
    bodyStats.addStats(_toAdd);
    ret.add(StatsContribution.getBodyContrib(bodyStats));
    List<StatsContribution> raceContribs=getRaceTraitsContrib(race,level);
    ret.addAll(raceContribs);
    List<StatsContribution> classContribs=getClassTraitsContrib(characterClass,level);
    ret.addAll(classContribs);
    return ret;
  }

  private List<StatsContribution> getRaceTraitsContrib(RaceDescription race, int level)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    List<TraitDescription> traits=race.getTraitsForLevel(level);
    for(TraitDescription trait : traits)
    {
      StatsProvider provider=trait.getStatsProvider();
      BasicStatsSet statsForTrait=provider.getStats(1,level);
      int nbStats=statsForTrait.getStatsCount();
      if (nbStats>0)
      {
        ret.add(StatsContribution.getTraitContrib(trait,statsForTrait));
      }
    }
    return ret;
  }

  private List<StatsContribution> getClassTraitsContrib(ClassDescription characterClass, int level)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    List<TraitDescription> traits=characterClass.getTraitsForLevel(level);
    for(TraitDescription trait : traits)
    {
      int traitID=trait.getIdentifier();
      if ((traitID==1879230292) || (traitID==1879231342))
      {
        continue; // Audacity
      }
      StatsProvider provider=trait.getStatsProvider();
      BasicStatsSet statsForTrait=provider.getStats(1,level);
      int nbStats=statsForTrait.getStatsCount();
      if (nbStats>0)
      {
        ret.add(StatsContribution.getTraitContrib(trait,statsForTrait));
      }
    }
    return ret;
  }
}
