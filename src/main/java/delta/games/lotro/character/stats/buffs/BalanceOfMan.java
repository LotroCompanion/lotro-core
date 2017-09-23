package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Buff 'Balance of Man' for Men.
 * @author DAM
 */
public class BalanceOfMan extends AbstractBuffImpl
{
  private static STAT[] TARGETS = { STAT.EVADE, STAT.PARRY, STAT.BLOCK };

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    int level=character.getLevel();
    float value;
    // 105: 848
    // 106: 1696
    // 107: 1880
    // 108: 2070?
    // 109: 2260
    // 110: 2440?
    // 111: 2630?
    // 112: 2820?
    // 113: 3010?
    // 114: 3200?
    // 115: 3392?
    // Giseldah:
    // For lvl106-115, Balance of Man and Shield Brawler follow
    // the 2-4x lvl105 scheme with rounded to a precision of 10 for in
    // between levels 107-114 (last digit always 0).

    if (level<=105) value=8.08f*level;
    else if ((level>=106) && (level<=115))
    {
      float factor=2+((level-106)*2.0f)/9;
      value=848*factor;
      if ((level>=107) && (level<=114))
      {
        value=10*(int)(value/10);
      }
    }
    else
    {
      value=0;
    }
    for(STAT stat : TARGETS)
    {
      stats.addStat(stat,new FixedDecimalsInteger(value));
    }
    return stats;
  }
}
