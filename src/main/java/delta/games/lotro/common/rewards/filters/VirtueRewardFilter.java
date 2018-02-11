package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Rewards;
import delta.games.lotro.common.Virtue;
import delta.games.lotro.common.VirtueId;

/**
 * Filter for rewards that contain a virtue.
 * @author DAM
 */
public class VirtueRewardFilter implements Filter<Rewards>
{
  private VirtueId _virtueId;

  /**
   * Constructor.
   * @param virtueId Virtue to select (may be <code>null</code>).
   */
  public VirtueRewardFilter(VirtueId virtueId)
  {
    _virtueId=virtueId;
  }

  /**
   * Get the virtue to use.
   * @return A virtue or <code>null</code>.
   */
  public VirtueId getVirtueId()
  {
    return _virtueId;
  }

  /**
   * Set the virtue to select.
   * @param virtueId Virtue to use, may be <code>null</code>.
   */
  public void setVirtueId(VirtueId virtueId)
  {
    _virtueId=virtueId;
  }

  public boolean accept(Rewards rewards)
  {
    if (_virtueId==null)
    {
      return true;
    }
    Virtue[] virtues=rewards.getVirtues();
    if (virtues!=null)
    {
      for(Virtue virtue : virtues)
      {
        if (_virtueId==virtue.getIdentifier())
        {
          return true;
        }
      }
    }
    return false;
  }
}
