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
    // TODO find out formula
    if (level<=105) value=8.08f*level;
    else if (level==106) value=1696;
    else /*if (level==107)*/ value=1880;
    for(STAT stat : TARGETS)
    {
      stats.addStat(stat,new FixedDecimalsInteger(value));
    }
    return stats;
  }
}
