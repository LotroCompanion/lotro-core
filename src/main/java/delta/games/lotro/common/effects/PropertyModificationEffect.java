package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * Effects that gives stats.
 * @author DAM
 */
public class PropertyModificationEffect extends Effect2
{
  private StatsProvider _statsProvider;

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Set the stats provider.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _statsProvider=statsProvider;
  }
}
