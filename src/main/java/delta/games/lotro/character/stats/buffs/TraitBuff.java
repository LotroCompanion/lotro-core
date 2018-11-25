package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Buff based on a trait.
 * @author DAM
 */
public class TraitBuff extends AbstractBuffImpl
{
  private TraitDescription _trait;

  /**
   * Constructor.
   * @param trait Trait to use.
   */
  public TraitBuff(TraitDescription trait)
  {
    _trait=trait;
  }

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    Integer tier=buff.getTier();
    StatsProvider statsProvider=_trait.getStatsProvider();
    int level=character.getLevel();
    BasicStatsSet stats=statsProvider.getStats(tier!=null?tier.intValue():1,level);
    return stats;
  }

  @Override
  public BasicStatsSet getStats(CharacterData character, BasicStatsSet raw, BuffInstance buff)
  {
    return null;
  }

  @Override
  public List<Integer> getTiers()
  {
    List<Integer> ret=new ArrayList<Integer>();
    int tiers=_trait.getTiersCount();
    if (tiers>1)
    {
      for(int i=1;i<=tiers;i++)
      {
        ret.add(Integer.valueOf(i));
      }
    }
    return ret;
  }
}
