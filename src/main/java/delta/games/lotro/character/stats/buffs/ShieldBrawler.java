package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Buff 'Shield Brawler' for Dwarves.
 * @author DAM
 */
public class ShieldBrawler extends AbstractBuffImpl
{
  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    int level=character.getLevel();

    // 100: 2020
    float value=20.2f*level;
    
    // Giseldah:
    // For lvl106-115, Balance of Man and Shield Brawler follow
    // the 2-4x lvl105 scheme with rounded to a precision of 10 for in
    // between levels 107-114 (last digit always 0).

    if (level<=105) value=20.2f*level;
    else if ((level>=106) && (level<=115))
    {
      float factor=2+((level-106)*2.0f)/9;
      value=2121*factor;
      if ((level>=107) && (level<=114))
      {
        value=10*(int)(value/10);
      }
    }
    else
    {
      value=0;
    }
    stats.addStat(STAT.BLOCK,new FixedDecimalsInteger(value));
    return stats;
  }
}
