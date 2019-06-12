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
  private static final STAT[] TARGETS = { STAT.MIGHT, STAT.VITALITY, STAT.WILL, STAT.FATE, STAT.AGILITY };

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    int level=character.getLevel();
    // TODO Formula is probably wrong
    int value=Math.min(level,85)+Math.max(level-85,0)*3;
    // Level 115: 247 (Update 23)
    if (level==115) value=247;
    // Level 120: 366 (Update 24)
    if (level==120) value=366;
    for(STAT stat : TARGETS)
    {
      stats.addStat(stat,new FixedDecimalsInteger(value));
    }
    return stats;
  }
}
