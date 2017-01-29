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
    float value=20.2f*level;
    stats.addStat(STAT.BLOCK,new FixedDecimalsInteger(value));
    return stats;
  }
}
