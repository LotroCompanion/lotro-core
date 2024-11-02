package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;

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
    for(StatProvider provider : _statsProvider.getStatProviders())
    {
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
    Float value=StatUtils.getStatValue(level,tierValue,_tiers,provider);
    return value;
  }
}
