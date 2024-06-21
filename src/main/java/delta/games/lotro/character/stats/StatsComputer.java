package delta.games.lotro.character.stats;

import delta.games.lotro.character.traits.EffectAtRank;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;

/**
 * Stats computing utility methods.
 * @author DAM
 */
public class StatsComputer
{
  /**
   * Get the stats for a given trait.
   * @param level Level to use.
   * @param trait Trait.
   * @param tier Tier to use.
   * @return some stats.
   */
  public static BasicStatsSet getStats(int level, TraitDescription trait, int tier)
  {
    StatsProvider statsProvider=trait.getStatsProvider();
    int tiersCount=trait.getTiersCount();
    BasicStatsSet stats=getStats(level,statsProvider,tiersCount,tier);
    BasicStatsSet statsFromEffects=getStatsFromEffectsAtRank(level,trait,tier);
    if (statsFromEffects.getStatsCount()>0)
    {
      stats.addStats(statsFromEffects);
    }
    return stats;
  }

  private static BasicStatsSet getStats(int level, StatsProvider statsProvider, int tiersCount, int tier)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(StatProvider provider : statsProvider.getStatProviders())
    {
      Float value=getStatValue(level,tier,tiersCount,provider);
      if (value!=null)
      {
        StatDescription stat=provider.getStat();
        stats.setStat(stat,provider.getOperator(),value,provider.getDescriptionOverride());
      }
    }
    return stats;
  }

  private static BasicStatsSet getStatsFromEffectsAtRank(int level, TraitDescription trait, int tier)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(EffectAtRank effectAtRank : trait.getEffects())
    {
      int rank=effectAtRank.getRank();
      if (rank==tier)
      {
        Effect effect=effectAtRank.getEffect();
        if (effect instanceof PropertyModificationEffect)
        {
          PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
          StatsProvider statsProvider=propModEffect.getStatsProvider();
          int tiersCount=trait.getTiersCount();
          stats=getStats(level,statsProvider,tiersCount,tier);
          break;
        }
      }
    }
    return stats;
  }

  private static Float getStatValue(int level, int tier, int tiersCount, StatProvider provider)
  {
    Float value=null;
    if (provider instanceof TieredScalableStatProvider)
    {
      value=provider.getStatValue(tier,level);
    }
    else if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableStatProvider=(ScalableStatProvider)provider;
      if (tiersCount>1)
      {
        value=scalableStatProvider.getStatValue(1,tier);
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
    return value;
  }
}
