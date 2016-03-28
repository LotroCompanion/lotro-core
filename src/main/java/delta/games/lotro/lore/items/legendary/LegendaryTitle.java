package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Legendary title description.
 * @author DAM
 */
public class LegendaryTitle
{
  private String _name;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param name Title name.
   */
  public LegendaryTitle(String name)
  {
    _name=name;
    _stats=new BasicStatsSet();
  }

  /**
   * Get the name of this title.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the stats for this title.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }
}
