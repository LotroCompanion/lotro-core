package delta.games.lotro.character.storage.statistics;

import delta.games.lotro.character.storage.statistics.reputation.StorageReputationStats;
import delta.games.lotro.common.money.Money;

/**
 * Storage statistics.
 * @author DAM
 */
public class StorageStatistics
{
  private long _totalItemXP;
  private StorageReputationStats _reputationStats;
  private Money _totalValue;

  /**
   * Constructor.
   */
  public StorageStatistics()
  {
    _totalItemXP=0;
    _reputationStats=new StorageReputationStats();
    _totalValue=new Money();
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
   * @return the totalValue
   */
  public Money getTotalValue()
  {
    return _totalValue;
  }

  /**
   * @param totalValue the totalValue to set
   */
  public void setTotalValue(Money totalValue)
  {
    _totalValue=totalValue;
  }
}
