package delta.games.lotro.character.stats.tomes;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Stat tome description.
 * @author DAM
 */
public class StatTome
{
  private StatDescription _stat;
  private int _rank;
  private int _traitId;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param rank Rank.
   * @param traitId Identifier of the associated trait.
   * @param stats Provided stats.
   */
  public StatTome(StatDescription stat, int rank, int traitId, BasicStatsSet stats)
  {
    _stat=stat;
    _rank=rank;
    _traitId=traitId;
    _stats=stats;
  }

  /**
   * Get the targeted stat.
   * @return a stat.
   */
  public StatDescription getStat()
  {
    return _stat;
  }

  /**
   * Get the tome rank.
   * @return the rank.
   */
  public int getRank()
  {
    return _rank;
  }

  /**
   * Get the identifier of the associated trait.
   * @return a trait identifier.
   */
  public int getTraitId()
  {
    return _traitId;
  }

  /**
   * Get the provided stats.
   * @return some stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  @Override
  public String toString()
  {
    return _stat.getName()+" rank "+_rank+" (trait="+_traitId+"): stats="+_stats;
  }
}
