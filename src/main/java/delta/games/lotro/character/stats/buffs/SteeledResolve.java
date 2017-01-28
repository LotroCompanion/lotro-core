package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

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

  @Override
  public List<Integer> getTiers()
  {
    List<Integer> ret=new ArrayList<Integer>();
    for(int tier=1;tier<=4;tier++) ret.add(Integer.valueOf(tier));
    return ret;
  }
}
