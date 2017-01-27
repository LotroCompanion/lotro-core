package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Steeled Resolve.
 * @author DAM
 */
public class SteeledResolve extends AbstractBuffImpl
{
  @Override
  public void apply(CharacterData character, BasicStatsSet raw, BuffInstance buff)
  {
    FixedDecimalsInteger morale=raw.getStat(STAT.MORALE);
    Integer tier=buff.getTier();
    if (tier!=null)
    {
      float factor=1+0.01f*tier.intValue();
      raw.setStat(STAT.MORALE,new FixedDecimalsInteger(morale.floatValue()*factor));
    }
  }
}
