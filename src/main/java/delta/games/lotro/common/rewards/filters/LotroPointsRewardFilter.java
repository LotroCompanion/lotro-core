package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain LOTRO points, or that do NOT contain LOTRO points.
 * @author DAM
 */
public class LotroPointsRewardFilter implements Filter<Rewards>
{
  private Boolean _hasLotroPoints;

  /**
   * Constructor.
   * @param hasLotroPoints Indicates if this filter shall select rewards
   * with or without LOTRO points (<code>null</code> means no filter).
   */
  public LotroPointsRewardFilter(Boolean hasLotroPoints)
  {
    _hasLotroPoints=hasLotroPoints;
  }

  /**
   * Get the value of the 'has LOTRO points' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasLotroPointsFlag()
  {
    return _hasLotroPoints;
  }

  /**
   * Set the value of the 'has LOTRO points' flag.
   * @param hasLotroPoints Flag to set, may be <code>null</code>.
   */
  public void setHasLotroPointsFlag(Boolean hasLotroPoints)
  {
    _hasLotroPoints=hasLotroPoints;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasLotroPoints==null)
    {
      return true;
    }
    int points=rewards.getLotroPoints();
    return (_hasLotroPoints.booleanValue())?(points>0):(points==0);
  }
}
