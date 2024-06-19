package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * A buff that gives predefined stats.
 * @author DAM
 */
public class SimpleStatsBuff extends AbstractBuffImpl
{
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param stats Stats.
   */
  public SimpleStatsBuff(BasicStatsSet stats)
  {
    _stats=stats;
  }

  @Override
  public BasicStatsSet getStats(int level, BuffInstance buff)
  {
    return _stats;
  }
}
