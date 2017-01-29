package delta.games.lotro.character.stats.base;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for base character stats.
 * @author DAM
 */
public class BaseStatsManager
{
  private HashMap<CharacterClass,HashMap<Integer,BasicStatsSet>> _lowLevelStatsByClass;
  private HashMap<Race,BasicStatsSet> _raceContrib;
  private BasicStatsSet _toAdd;

  /**
   * Constructor.
   */
  public BaseStatsManager()
  {
    _toAdd=new BasicStatsSet();
    _toAdd.setStat(STAT.PHYSICAL_MASTERY,1);
    _toAdd.setStat(STAT.TACTICAL_MASTERY,1);
    _toAdd.setStat(STAT.BLOCK,new FixedDecimalsInteger(1.5f));
    initRaceContribs();
    loadLowLevelStatsTables();
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
    BasicStatsSet classSet=getBaseStats(cClass,level);
    BasicStatsSet raceSet=_raceContrib.get(race);
    BasicStatsSet levelSet=getLevelContrib(cClass,level);
    BasicStatsSet global=new BasicStatsSet();
    global.addStats(classSet);
    global.addStats(raceSet);
    global.addStats(levelSet);
    global.addStats(_toAdd);
    // Lore-master: Ancient Wisdom
    if ((cClass==CharacterClass.LORE_MASTER) && (level>=28))
    {
      global.addStat(STAT.WILL,new FixedDecimalsInteger(1.1f*level));
    }
    return global;
  }

  private BasicStatsSet getLevelContrib(CharacterClass cClass, int level)
  {
    BasicStatsSet set=new BasicStatsSet();
    // Power
    if (cClass!=CharacterClass.BEORNING)
    {
      int power;
      if ((cClass==CharacterClass.MINSTREL) ||
          (cClass==CharacterClass.LORE_MASTER) ||
          (cClass==CharacterClass.RUNE_KEEPER)) {
        power=level*65;
      }
      else
      {
        power=level*50;
      }
      set.setStat(STAT.POWER,power);
    }
    // Critical defence
    // TODO As a buff
    float critDef=0;
    if (cClass==CharacterClass.CAPTAIN) critDef=6.06f*level;
    else if (cClass==CharacterClass.GUARDIAN) critDef=10.1f*level;
    else if (cClass==CharacterClass.WARDEN) critDef=20*level;
    if (critDef>0)
    {
      set.setStat(STAT.CRITICAL_DEFENCE,critDef);
    }
    return set;
  }

  private BasicStatsSet getBaseStats(CharacterClass cClass, int level)
  {
    if (level<=50)
    {
      HashMap<Integer,BasicStatsSet> table=_lowLevelStatsByClass.get(cClass);
      if (table!=null)
      {
        return table.get(Integer.valueOf(level));
      }
      return null;
    }
    if (cClass==CharacterClass.BEORNING) return getBaseStatsBeorning(level);
    if (cClass==CharacterClass.BURGLAR) return getBaseStatsBurglar(level);
    if (cClass==CharacterClass.CHAMPION) return getBaseStatsChampion(level);
    if (cClass==CharacterClass.CAPTAIN) return getBaseStatsCaptain(level);
    if (cClass==CharacterClass.GUARDIAN) return getBaseStatsGuardian(level);
    if (cClass==CharacterClass.HUNTER) return getBaseStatsHunter(level);
    if (cClass==CharacterClass.LORE_MASTER) return getBaseStatsLoreMaster(level);
    if (cClass==CharacterClass.MINSTREL) return getBaseStatsMinstrel(level);
    if (cClass==CharacterClass.RUNE_KEEPER) return getBaseStatsRuneKeeper(level);
    if (cClass==CharacterClass.WARDEN) return getBaseStatsWarden(level);
    return null;
  }

  private BasicStatsSet getBaseStatsBeorning(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 83);
    set.setStat(STAT.AGILITY, 57);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 80);
    set.setStat(STAT.FATE, 92);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 22*level + 276;
    else if (level == 66) morale = 1733;
    else if (level == 67) morale = 1766;
    else if (level == 68) morale = 1804;
    else if (level == 69) morale = 1848;
    else if (level == 70) morale = 1897;
    else if (level == 71) morale = 1952;
    else if (level >= 72) morale = 66*level - 2740; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(80.5 + level * (2/3));
    set.setStat(STAT.ICMR, icmr);
    // NCMR
    set.setStat(STAT.OCMR, 240);
    return set;
  }

  private BasicStatsSet getBaseStatsBurglar(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 83);
    set.setStat(STAT.AGILITY, 92);
    set.setStat(STAT.VITALITY, 57);
    set.setStat(STAT.WILL, 80);
    set.setStat(STAT.FATE, 59);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 22*level + 186;
    else if (level == 66) morale = 1643;
    else if (level == 67) morale = 1676;
    else if (level == 68) morale = 1714;
    else if (level == 69) morale = 1758;
    else if (level == 70) morale = 1807;
    else if (level == 71) morale = 1862;
    else if (level >= 72) morale = 66*level - 2830; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(71.1 + level * 0.6);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 120);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsCaptain(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 83);
    set.setStat(STAT.AGILITY, 57);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 80);
    set.setStat(STAT.FATE, 92);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 22*level + 276;
    else if (level == 66) morale = 1733;
    else if (level == 67) morale = 1766;
    else if (level == 68) morale = 1804;
    else if (level == 69) morale = 1848;
    else if (level == 70) morale = 1897;
    else if (level == 71) morale = 1952;
    else if (level >= 72) morale = 66*level - 2740; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(80.5 + level * 0.66666);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 240);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsChampion(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 92);
    set.setStat(STAT.AGILITY, 83);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 58);
    set.setStat(STAT.FATE, 81);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 27*level + 274;
    else if (level == 66) morale = 2062;
    else if (level == 67) morale = 2102;
    else if (level == 68) morale = 2149;
    else if (level == 69) morale = 2203;
    else if (level == 70) morale = 2263;
    else if (level == 71) morale = 2330;
    else if (level >= 72) morale = 81*level - 3428; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(91.25 + level * 0.75);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 300);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsGuardian(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 83);
    set.setStat(STAT.AGILITY, 59);
    set.setStat(STAT.VITALITY, 92);
    set.setStat(STAT.WILL, 80);
    set.setStat(STAT.FATE, 57);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 27*level + 301;
    else if (level == 66) morale = 2089;
    else if (level == 67) morale = 2129;
    else if (level == 68) morale = 2176;
    else if (level == 69) morale = 2230;
    else if (level == 70) morale = 2290;
    else if (level == 71) morale = 2357;
    else if (level >= 72) morale = 81*level - 3401; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(91.25 + level * 0.75);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 300);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsHunter(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 57);
    set.setStat(STAT.AGILITY, 112);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 83);
    set.setStat(STAT.FATE, 60);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 22*level + 186;
    else if (level == 66) morale = 1643;
    else if (level == 67) morale = 1676;
    else if (level == 68) morale = 1714;
    else if (level == 69) morale = 1758;
    else if (level == 70) morale = 1807;
    else if (level == 71) morale = 1862;
    else if (level >= 72) morale = 66*level - 2830; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(80.5 + level * 0.66666);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 240);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsLoreMaster(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 57);
    set.setStat(STAT.AGILITY, 80);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 83);
    set.setStat(STAT.FATE, 92);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 15*level + 193;
    else if (level == 66) morale = 1186;
    else if (level == 67) morale = 1208;
    else if (level == 68) morale = 1234;
    else if (level == 69) morale = 1264;
    else if (level == 70) morale = 1297;
    else if (level == 71) morale = 1334;
    else if (level >= 72) morale = 45*level - 1865; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(71.1 + level * 0.6);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 120);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsMinstrel(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 80);
    set.setStat(STAT.AGILITY, 57);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 92);
    set.setStat(STAT.FATE, 83);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 15*level + 193;
    else if (level == 66) morale = 1186;
    else if (level == 67) morale = 1208;
    else if (level == 68) morale = 1234;
    else if (level == 69) morale = 1264;
    else if (level == 70) morale = 1297;
    else if (level == 71) morale = 1334;
    else if (level >= 72) morale = 45*level - 1865; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(70.1 + level * 0.6);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 120);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsRuneKeeper(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 58);
    set.setStat(STAT.AGILITY, 81);
    set.setStat(STAT.VITALITY, 60);
    set.setStat(STAT.WILL, 84);
    set.setStat(STAT.FATE, 93);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 15*level + 193;
    else if (level == 66) morale = 1186;
    else if (level == 67) morale = 1208;
    else if (level == 68) morale = 1234;
    else if (level == 69) morale = 1264;
    else if (level == 70) morale = 1297;
    else if (level == 71) morale = 1334;
    else if (level >= 72) morale = 45*level - 1865; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(71.1 + level * 0.6);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 120);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private BasicStatsSet getBaseStatsWarden(int level)
  {
    BasicStatsSet set = new BasicStatsSet();
    set.setStat(STAT.MIGHT, 83);
    set.setStat(STAT.AGILITY, 92);
    set.setStat(STAT.VITALITY, 59);
    set.setStat(STAT.WILL, 57);
    set.setStat(STAT.FATE, 80);
    // Morale
    int morale = 0;
    if (level <= 65) morale = 27*level + 301;
    else if (level == 66) morale = 2089;
    else if (level == 67) morale = 2129;
    else if (level == 68) morale = 2176;
    else if (level == 69) morale = 2230;
    else if (level == 70) morale = 2290;
    else if (level == 71) morale = 2357;
    else if (level >= 72) morale = 81*level - 3401; // level >= 72
    set.setStat(STAT.MORALE, morale);
    // ICMR
    int icmr = (int)(91.25 + level * 0.75);
    set.setStat(STAT.ICMR, icmr);
    // OCMR
    set.setStat(STAT.OCMR, 300);
    // ICPR
    set.setStat(STAT.ICPR, 240);
    // OCPR
    set.setStat(STAT.OCPR, 120);
    return set;
  }

  private void loadLowLevelStatsTables()
  {
    _lowLevelStatsByClass=new HashMap<CharacterClass,HashMap<Integer,BasicStatsSet>>();
    for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
    {
      HashMap<Integer,BasicStatsSet> table=loadStatsTable(cClass);
      if (table!=null)
      {
        _lowLevelStatsByClass.put(cClass,table);
      }
    }
  }

  private HashMap<Integer,BasicStatsSet> loadStatsTable(CharacterClass cClass)
  {
    HashMap<Integer,BasicStatsSet> ret=new HashMap<Integer,BasicStatsSet>();
    URL url=URLTools.getFromClassPath(cClass.getKey()+".txt",BaseStatsManager.class.getPackage());
    if (url!=null)
    {
      TextFileReader r=new TextFileReader(url, EncodingNames.ISO8859_1);
      List<String> lines=TextUtils.readAsLines(r);
      List<STAT> stats=new ArrayList<STAT>();
      String[] statStrs=lines.get(0).split("\t");
      for(String statStr : statStrs)
      {
        if (!statStr.equals("Level"))
        {
          STAT stat=STAT.getByName(statStr);
          stats.add(stat);
        }
      }
      lines.remove(0);
      for(String statLine : lines)
      {
        String[] items=statLine.split("\t");
        int level=NumericTools.parseInt(items[0],-1);
        if (level!=-1)
        {
          BasicStatsSet set=new BasicStatsSet();
          int index=1;
          for(STAT stat : stats)
          {
            if (index<items.length)
            {
              int value=NumericTools.parseInt(items[index],0);
              set.setStat(stat,value);
            }
            index++;
          }
          ret.put(Integer.valueOf(level),set);
        }
      }
    }
    return ret;
  }
}
