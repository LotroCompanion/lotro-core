package delta.games.lotro.character.stats.base;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.io.StartStatsManagerIO;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for base character stats.
 * @author DAM
 */
public class BaseStatsManager
{
  private StartStatsManager _startStatsManager;
  private HashMap<Race,BasicStatsSet> _raceContrib;
  private BasicStatsSet _toAdd;

  /**
   * Constructor.
   */
  public BaseStatsManager()
  {
    _startStatsManager=StartStatsManagerIO.load();
    _toAdd=new BasicStatsSet();
    _toAdd.setStat(STAT.PHYSICAL_MASTERY,1);
    _toAdd.setStat(STAT.TACTICAL_MASTERY,1);
    _toAdd.setStat(STAT.BLOCK,new FixedDecimalsInteger(1.5f));
    initRaceContribs();
  }

  private void initRaceContribs()
  {
    _raceContrib=new HashMap<Race,BasicStatsSet>();
    // Man
    BasicStatsSet man=new BasicStatsSet();
    man.setStat(STAT.MIGHT, 15);
    man.setStat(STAT.WILL, -7);
    man.setStat(STAT.FATE, 15);
    man.setStat(STAT.INCOMING_HEALING_PERCENTAGE,5);
    _raceContrib.put(Race.MAN,man);
    // Dwarf
    BasicStatsSet dwarf=new BasicStatsSet();
    dwarf.setStat(STAT.MIGHT, 15);
    dwarf.setStat(STAT.AGILITY, -7);
    dwarf.setStat(STAT.VITALITY, 10);
    dwarf.setStat(STAT.FATE, -7);
    dwarf.setStat(STAT.ICMR, 30);
    dwarf.setStat(STAT.OCMR, -60);
    dwarf.setStat(STAT.ICPR, 30);
    dwarf.setStat(STAT.OCPR, -30);
    dwarf.setStat(STAT.PHYSICAL_MITIGATION_PERCENTAGE, 1);
    _raceContrib.put(Race.DWARF,dwarf);
    // Elf
    BasicStatsSet elf=new BasicStatsSet();
    elf.setStat(STAT.MORALE, -20);
    elf.setStat(STAT.AGILITY, 15);
    elf.setStat(STAT.FATE, -7);
    elf.setStat(STAT.OCMR, -60);
    _raceContrib.put(Race.ELF,elf);
    // High Elf
    BasicStatsSet highElf=new BasicStatsSet();
    highElf.setStat(STAT.WILL, -7);
    highElf.setStat(STAT.FATE, -7);
    highElf.setStat(STAT.MORALE, 20);
    highElf.setStat(STAT.OCMR, 60);
    // TODO Suffer No Illness: 1% Disease Resistance, 1% Poison Resistance
    _raceContrib.put(Race.HIGH_ELF,highElf);
    // Hobbit
    BasicStatsSet hobbit=new BasicStatsSet();
    // - TODO Hobbit Courage: +1% Fear Resistance. 
    // - Small Size 
    hobbit.setStat(STAT.MIGHT, -7);
    // - Hobbit-toughness
    hobbit.setStat(STAT.VITALITY, 15);
    // - Rapid Recovery
    hobbit.setStat(STAT.OCMR, 60);
    // - Resist Corruption
    hobbit.setStat(STAT.SHADOW_MITIGATION_PERCENTAGE, 1);
    _raceContrib.put(Race.HOBBIT,hobbit);
    // Beorning
    BasicStatsSet beorning=new BasicStatsSet();
    beorning.setStat(STAT.MIGHT, 15);
    beorning.setStat(STAT.VITALITY, 15);
    beorning.setStat(STAT.FATE, -7);
    _raceContrib.put(Race.BEORNING,beorning);
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
    BasicStatsSet raceSet=_raceContrib.get(race);
    BasicStatsSet classTraitsSet=getClassTraitsContrib(cClass,level);
    BasicStatsSet global=new BasicStatsSet();
    global.addStats(classSet);
    global.addStats(raceSet);
    global.addStats(classTraitsSet);
    global.addStats(_toAdd);
    return global;
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
      BasicStatsSet statsForTrait=provider.getStats(1,level);
      int nbStats=statsForTrait.getStatsCount();
      if (nbStats>0)
      {
        stats.addStats(statsForTrait);
      }
    }
    return stats;
  }
}
