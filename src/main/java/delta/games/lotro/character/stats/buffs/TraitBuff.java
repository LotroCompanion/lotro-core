package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.utils.FixedDecimalsInteger;

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
    int nbTiers=_trait.getTiersCount();
    BasicStatsSet stats=new BasicStatsSet();
    int nbStats=statsProvider.getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider provider=statsProvider.getStatProvider(i);
      int tierValue=(tier!=null)?tier.intValue():1;
      Float value=null;
      if (provider instanceof TieredScalableStatProvider)
      {
        value=provider.getStatValue(tierValue,level);
      }
      else if (provider instanceof ScalableStatProvider)
      {
        ScalableStatProvider scalableStatProvider=(ScalableStatProvider)provider;
        if (nbTiers>1)
        {
          value=scalableStatProvider.getStatValue(1,tierValue);
        }
        else
        {
          value=scalableStatProvider.getStatValue(1,level);
        }
      }
      else
      {
        value=provider.getStatValue(1,level);
      }
      if (value!=null)
      {
        STAT stat=provider.getStat();
        stats.setStat(stat,new FixedDecimalsInteger(value.floatValue()));
      }
    }
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
