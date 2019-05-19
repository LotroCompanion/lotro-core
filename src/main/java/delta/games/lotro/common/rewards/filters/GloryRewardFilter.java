package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain glory, or that do NOT contain glory.
 * @author DAM
 */
public class GloryRewardFilter implements Filter<Rewards>
{
  private Boolean _hasGlory;

  /**
   * Constructor.
   * @param hasGlory Indicates if this filter shall select rewards
   * with or without glory (<code>null</code> means no filter).
   */
  public GloryRewardFilter(Boolean hasGlory)
  {
    _hasGlory=hasGlory;
  }

  /**
   * Get the value of the 'has glory' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasGloryFlag()
  {
    return _hasGlory;
  }

  /**
   * Set the value of the 'has XP' flag.
   * @param hasGlory Flag to set, may be <code>null</code>.
   */
  public void setHasGloryFlag(Boolean hasGlory)
  {
    _hasGlory=hasGlory;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasGlory==null)
    {
      return true;
    }
    int glory=rewards.getGlory();
    return (_hasGlory.booleanValue())?(glory>0):(glory==0);
  }
}
