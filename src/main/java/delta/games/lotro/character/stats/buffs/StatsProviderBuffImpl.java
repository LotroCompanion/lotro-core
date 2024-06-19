package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.TieredScalableStatProvider;

/**
 * Buff based on a stats provider.
 * @author DAM
 */
public class StatsProviderBuffImpl extends AbstractBuffImpl
{
  private StatsProvider _statsProvider;
  private int _tiers;

  /**
   * Constructor.
   * @param statsProvider Stats provider.
   * @param tiers Number of tiers.
   */
  public StatsProviderBuffImpl(StatsProvider statsProvider, int tiers)
  {
    _statsProvider=statsProvider;
    _tiers=tiers;
  }

  @Override
  public BasicStatsSet getStats(int level, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    int nbStats=_statsProvider.getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider provider=_statsProvider.getStatProvider(i);
      Float value=getStatValue(level,buff,provider);
      if (value!=null)
      {
        StatDescription stat=provider.getStat();
        stats.setStat(stat,provider.getOperator(),value,provider.getDescriptionOverride());
      }
    }
    return stats;
  }

  @Override
  public List<Integer> getTiers()
  {
    List<Integer> ret=new ArrayList<Integer>();
    if (_tiers>1)
    {
      for(int i=1;i<=_tiers;i++)
      {
        ret.add(Integer.valueOf(i));
      }
    }
    return ret;
  }

  private Float getStatValue(int level, BuffInstance buff, StatProvider provider)
  {
    Integer tier=buff.getTier();
    int tierValue=(tier!=null)?tier.intValue():1;
    Float value=null;
    if (provider instanceof TieredScalableStatProvider)
    {
      value=provider.getStatValue(tierValue,level);
    }
    else if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableStatProvider=(ScalableStatProvider)provider;
      if (_tiers>1)
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
    return value;
  }
}
