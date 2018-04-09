package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Rewards;

/**
 * Filter for rewards that contain destiny points, or that do NOT contain destiny points.
 * @author DAM
 */
public class DestinyPointsRewardFilter implements Filter<Rewards>
{
  private Boolean _hasDestinyPoints;

  /**
   * Constructor.
   * @param hasDestinyPoints Indicates if this filter shall select rewards
   * with or without destiny points (<code>null</code> means no filter).
   */
  public DestinyPointsRewardFilter(Boolean hasDestinyPoints)
  {
    _hasDestinyPoints=hasDestinyPoints;
  }

  /**
   * Get the value of the 'has destiny points' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasDestinyPointsFlag()
  {
    return _hasDestinyPoints;
  }

  /**
   * Set the value of the 'has destiny points' flag.
   * @param hasDestinyPoints Flag to set, may be <code>null</code>.
   */
  public void setHasLotroPointsFlag(Boolean hasDestinyPoints)
  {
    _hasDestinyPoints=hasDestinyPoints;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasDestinyPoints==null)
    {
      return true;
    }
    int points=rewards.getDestinyPoints();
    return (_hasDestinyPoints.booleanValue())?(points>0):(points==0);
  }
}
