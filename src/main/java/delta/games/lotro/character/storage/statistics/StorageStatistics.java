package delta.games.lotro.character.storage.statistics;

import delta.games.lotro.character.storage.statistics.reputation.StorageReputationStats;

/**
 * Storage statistics.
 * @author DAM
 */
public class StorageStatistics
{
  private long _totalItemXP;
  private StorageReputationStats _reputationStats;

  /**
   * Constructor.
   */
  public StorageStatistics()
  {
    _totalItemXP=0;
  }

  /**
   * Get the total item XP.
   * @return the total item XP.
   */
  public long getTotalItemXP()
  {
    return _totalItemXP;
  }

  /**
   * Set the total item XP.
   * @param totalItemXP the value to set.
   */
  public void setTotalItemXP(long totalItemXP)
  {
    _totalItemXP=totalItemXP;
  }

  /**
   * Get the reputation stats.
   * @return the reputation stats.
   */
  public StorageReputationStats getReputationStats()
  {
    return _reputationStats;
  }

  /**
   * Set the reputation stats.
   * @param reputationStats the reputation stats to set.
   */
  public void setReputationStats(StorageReputationStats reputationStats)
  {
    _reputationStats=reputationStats;
  }
}
