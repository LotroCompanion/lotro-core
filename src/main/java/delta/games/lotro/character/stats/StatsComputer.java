package delta.games.lotro.character.stats;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.traits.TraitDescription;
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
   * @param character Character to use.
   * @param trait Trait.
   * @param tier Tier to use.
   * @return some stats.
   */
  public static BasicStatsSet getStats(CharacterData character, TraitDescription trait, int tier)
  {
    StatsProvider statsProvider=trait.getStatsProvider();
    BasicStatsSet stats=new BasicStatsSet();
    int nbStats=statsProvider.getNumberOfStatProviders();
    int tiersCount=trait.getTiersCount();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider provider=statsProvider.getStatProvider(i);
      Float value=getStatValue(character,tier,tiersCount,provider);
      if (value!=null)
      {
        StatDescription stat=provider.getStat();
        stats.setStat(stat,provider.getOperator(),value,provider.getDescriptionOverride());
      }
    }
    return stats;
  }

  private static Float getStatValue(CharacterData character, int tier, int tiersCount, StatProvider provider)
  {
    int level=character.getLevel();
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
