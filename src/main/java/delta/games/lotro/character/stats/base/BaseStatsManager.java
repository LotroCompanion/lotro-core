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
    // TODO +5% incoming healing
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
    // TODO +1% Physical Mitigation (melee+ranged)
    _raceContrib.put(Race.DWARF,dwarf);
    // Elf
    BasicStatsSet elf=new BasicStatsSet();
    elf.setStat(STAT.MORALE, -20);
    elf.setStat(STAT.AGILITY, 15);
    elf.setStat(STAT.FATE, -7);
    dwarf.setStat(STAT.OCMR, -60);
    _raceContrib.put(Race.ELF,elf);
    // Hobbit
    BasicStatsSet hobbit=new BasicStatsSet();
    hobbit.setStat(STAT.MIGHT, -7);
    hobbit.setStat(STAT.VITALITY, 15);
    hobbit.setStat(STAT.OCMR, 60);
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
    int critDef=0;
    if (cClass==CharacterClass.CAPTAIN) critDef=6*level;
    else if (cClass==CharacterClass.GUARDIAN) critDef=10*level;
    else if (cClass==CharacterClass.WARDEN) critDef=20*level;
    if (critDef!=0)
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
    // TODO other classes...
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
  /*
Function CalcBase(ByVal ClName As String, ByVal SName As String, ByVal CharLvl As Integer) As Double

  ElseIf CN = "CAPTAIN" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(92+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(22*L+276+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(1733+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(1766+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(1804+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(1848+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(1897+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(1952+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(66*L-2740+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT((2/3)*L+80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "GUARDIAN" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(92+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(27*L+301+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(2089+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(2129+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(2176+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(2230+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(2290+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(2357+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(81*L-3401+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT(0.75*L+90.75+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(300+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "HUNTER" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(112+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(60+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(22*L+186+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(1643+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(1676+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(1714+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(1758+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(1807+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(1862+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(66*L-2830+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT((2/3)*L+80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "LOREMASTER" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(92+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(15*L+193+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(1186+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(1208+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(1234+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(1264+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(1297+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(1334+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(45*L-1865+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT(0.6*L+69.6+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "MINSTREL" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(92+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(15*L+193+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(1186+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(1208+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(1234+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(1264+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(1297+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(1334+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(45*L-1865+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT(0.6*L+69.6+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "RUNEKEEPER" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(58+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(81+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(60+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(84+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(93+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(15*L+193+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(1186+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(1208+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(1234+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(1264+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(1297+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(1334+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(45*L-1865+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT(0.6*L+69.6+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  ElseIf CN = "WARDEN" Then
    If SN = "MIGHT" Then
      If 51 <= L Then
        Result = CDBL(INT(83+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "AGILITY" Then
      If 51 <= L Then
        Result = CDBL(INT(92+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "VITALITY" Then
      If 51 <= L Then
        Result = CDBL(INT(59+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "WILL" Then
      If 51 <= L Then
        Result = CDBL(INT(57+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "FATE" Then
      If 51 <= L Then
        Result = CDBL(INT(80+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "MORALE" Then
      If 51 <= L AND L <= 65 Then
        Result = CDBL(INT(27*L+301+0.500000000001))
      ElseIf L = 66 Then
        Result = CDBL(INT(2089+0.500000000001))
      ElseIf L = 67 Then
        Result = CDBL(INT(2129+0.500000000001))
      ElseIf L = 68 Then
        Result = CDBL(INT(2176+0.500000000001))
      ElseIf L = 69 Then
        Result = CDBL(INT(2230+0.500000000001))
      ElseIf L = 70 Then
        Result = CDBL(INT(2290+0.500000000001))
      ElseIf L = 71 Then
        Result = CDBL(INT(2357+0.500000000001))
      ElseIf 72 <= L Then
        Result = CDBL(INT(81*L-3401+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICMR" Then
      If 51 <= L Then
        Result = CDBL(INT(0.75*L+90.75+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCMR" Then
      If 51 <= L Then
        Result = CDBL(INT(300+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "ICPR" Then
      If 51 <= L Then
        Result = CDBL(INT(240+0.500000000001))
      Else
        Result = 0
      EndIf
    ElseIf SN = "NCPR" Then
      If 51 <= L Then
        Result = CDBL(INT(120+0.500000000001))
      Else
        Result = 0
      EndIf
    Else
      Result = 0
    EndIf
  Else
    Result = 0
  EndIf

  CalcBase = Result

End Function
 */
}
