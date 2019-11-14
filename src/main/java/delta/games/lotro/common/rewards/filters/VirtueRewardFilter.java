package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.virtues.VirtueDescription;
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
  private VirtueDescription _virtue;

  /**
   * Constructor.
   * @param virtue Virtue to select (may be <code>null</code>).
   */
  public VirtueRewardFilter(VirtueDescription virtue)
  {
    _virtue=virtue;
  }

  /**
   * Get the virtue to use.
   * @return A virtue or <code>null</code>.
   */
  public VirtueDescription getVirtue()
  {
    return _virtue;
  }

  /**
   * Set the virtue to select.
   * @param virtue Virtue to use, may be <code>null</code>.
   */
  public void setVirtue(VirtueDescription virtue)
  {
    _virtue=virtue;
  }

  public boolean accept(Rewards rewards)
  {
    if (_virtue==null)
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
    return (_virtue==virtueReward.getVirtue());
  }
}
