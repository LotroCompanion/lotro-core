package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.VirtueReward;

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
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof VirtueReward)
      {
        VirtueReward virtueReward=(VirtueReward)rewardElement;
        if (accept(virtueReward))
        {
          return true;
        }
      }
      else if (rewardElement instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectable=(SelectableRewardElement)rewardElement;
        if (accept(selectable.getElements()))
        {
          return true;
        }
      }
    }
    return false;
  }

  private boolean accept(VirtueReward virtueReward)
  {
    return (_virtueId==virtueReward.getIdentifier());
  }
}
