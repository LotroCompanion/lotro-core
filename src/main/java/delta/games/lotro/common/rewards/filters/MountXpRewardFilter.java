package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain mount XP, or that do NOT contain mount XP.
 * @author DAM
 */
public class MountXpRewardFilter implements Filter<Rewards>
{
  private Boolean _hasMountXp;

  /**
   * Constructor.
   * @param hasMountXp Indicates if this filter shall select rewards
   * with or without mount XP (<code>null</code> means no filter).
   */
  public MountXpRewardFilter(Boolean hasMountXp)
  {
    _hasMountXp=hasMountXp;
  }

  /**
   * Get the value of the 'has mount XP' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasMountXpFlag()
  {
    return _hasMountXp;
  }

  /**
   * Set the value of the 'has mount XP' flag.
   * @param hasMountXp Flag to set, may be <code>null</code>.
   */
  public void setHasItemXpFlag(Boolean hasMountXp)
  {
    _hasMountXp=hasMountXp;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasMountXp==null)
    {
      return true;
    }
    int mountXp=rewards.getMountXp();
    return (_hasMountXp.booleanValue())?(mountXp>0):(mountXp==0);
  }
}
