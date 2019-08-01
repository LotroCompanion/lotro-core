package delta.games.lotro.lore.items.sets;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * Bonus of an items set.
 * @author DAM
 */
public class ItemsSetBonus
{
  private int _piecesCount;
  private StatsProvider _stats;

  /**
   * Constructor.
   * @param piecesCount Number of pieces to activate the bonus.
   */
  public ItemsSetBonus(int piecesCount)
  {
    _piecesCount=piecesCount;
  }

  /**
   * Get the number of pieces for this bonus.
   * @return a pieces count.
   */
  public int getPiecesCount()
  {
    return _piecesCount;
  }

  /**
   * Get the bonus.
   * @return a stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _stats;
  }

  /**
   * Set the bonus.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _stats=statsProvider;
  }

  @Override
  public String toString()
  {
    return _piecesCount+": "+_stats;
  }
}
