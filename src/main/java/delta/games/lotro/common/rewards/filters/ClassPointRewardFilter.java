package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Rewards;

/**
 * Filter for rewards that give a class point, or that do NOT give a class point.
 * @author DAM
 */
public class ClassPointRewardFilter implements Filter<Rewards>
{
  private Boolean _hasClassPoint;

  /**
   * Constructor.
   * @param hasClassPoint Indicates if this filter shall select rewards
   * with or without a class point (<code>null</code> means no filter).
   */
  public ClassPointRewardFilter(Boolean hasClassPoint)
  {
    _hasClassPoint=hasClassPoint;
  }

  /**
   * Get the value of the 'has class point' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasClassPointFlag()
  {
    return _hasClassPoint;
  }

  /**
   * Set the value of the 'has LOTRO points' flag.
   * @param hasClassPoint Flag to set, may be <code>null</code>.
   */
  public void setHasClassPointFlag(Boolean hasClassPoint)
  {
    _hasClassPoint=hasClassPoint;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasClassPoint==null)
    {
      return true;
    }
    int points=rewards.getClassPoints();
    return (_hasClassPoint.booleanValue())?(points>0):(points==0);
  }
}
