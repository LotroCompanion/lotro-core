package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Motivation.
 * @author DAM
 */
public class Motivated extends AbstractBuffImpl
{
  @Override
  public void apply(CharacterData character, BasicStatsSet raw, BuffInstance buff)
  {
    FixedDecimalsInteger morale=raw.getStat(STAT.MORALE);
    raw.setStat(STAT.MORALE,new FixedDecimalsInteger(morale.floatValue()*1.1f));
  }
}
