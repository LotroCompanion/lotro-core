package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats provider.
 * @author DAM
 */
public class StatsProvider
{
  private List<StatProvider> _stats;

  /**
   * Constructor.
   */
  public StatsProvider()
  {
    _stats=new ArrayList<StatProvider>();
  }

  /**
   * Add a stat provider.
   * @param statProvider Stat provider to add.
   */
  public void addStatProvider(StatProvider statProvider)
  {
    _stats.add(statProvider);
  }

  /**
   * Get the number of stats providers.
   * @return a count.
   */
  public int getNumberOfStatProviders()
  {
    return _stats.size();
  }

  /**
   * Get the stat provider at the given index.
   * @param index Index of provider, starting at 0.
   * @return A stat provider.
   */
  public StatProvider getStatProvider(int index)
  {
    return _stats.get(index);
  }

  /**
   * Compute stats for a given tier and level.
   * @param tier Tier to use, starting at 1.
   * @param level Level to use, starting at 1.
   * @return A set of stats.
   */
  public BasicStatsSet getStats(int tier, int level)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(StatProvider provider : _stats)
    {
      Float value=provider.getStatValue(tier,level);
      if (value!=null)
      {
        STAT stat=provider.getStat();
        stats.setStat(stat,new FixedDecimalsInteger(value.floatValue()));
      }
    }
    return stats;
  }
}
