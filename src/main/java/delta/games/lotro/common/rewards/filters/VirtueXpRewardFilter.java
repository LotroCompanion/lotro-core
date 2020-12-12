package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain virtue XP, or that do NOT contain virtue XP.
 * @author DAM
 */
public class VirtueXpRewardFilter implements Filter<Rewards>
{
  private Boolean _hasVirtueXp;

  /**
   * Constructor.
   * @param hasVirtueXp Indicates if this filter shall select rewards
   * with or without virtue XP (<code>null</code> means no filter).
   */
  public VirtueXpRewardFilter(Boolean hasVirtueXp)
  {
    _hasVirtueXp=hasVirtueXp;
  }

  /**
   * Get the value of the 'has virtue XP' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasVirtueXpFlag()
  {
    return _hasVirtueXp;
  }

  /**
   * Set the value of the 'has virtue XP' flag.
   * @param hasVirtueXp Flag to set, may be <code>null</code>.
   */
  public void setHasVirtueXpFlag(Boolean hasVirtueXp)
  {
    _hasVirtueXp=hasVirtueXp;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasVirtueXp==null)
    {
      return true;
    }
    int virtueXp=rewards.getVirtueXp();
    return (_hasVirtueXp.booleanValue())?(virtueXp>0):(virtueXp==0);
  }
}
