package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Buff 'In Defence of Middle Earth' for Captains.
 * @author DAM
 */
public class InDefenceOfMiddleEarth extends AbstractBuffImpl
{
  private static STAT[] TARGETS = { STAT.MIGHT, STAT.VITALITY, STAT.WILL, STAT.FATE, STAT.AGILITY };

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    int level=character.getLevel();
    int value=Math.min(level,85)+Math.max(level-85,0)*3;
    for(STAT stat : TARGETS)
    {
      stats.addStat(stat,new FixedDecimalsInteger(value));
    }
    return stats;
  }
}
