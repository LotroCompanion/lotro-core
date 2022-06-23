package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.rewards.BillingTokenReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;

/**
 * Filter for rewards that contain a billing group.
 * @author DAM
 */
public class BillingGroupRewardFilter implements Filter<Rewards>
{
  private BillingGroup _billingGroup;

  /**
   * Constructor.
   * @param billingGroup Billing group to select (may be <code>null</code>).
   */
  public BillingGroupRewardFilter(BillingGroup billingGroup)
  {
    _billingGroup=billingGroup;
  }

  /**
   * Get the billing group to use.
   * @return A billing group or <code>null</code>.
   */
  public BillingGroup getBillingGroup()
  {
    return _billingGroup;
  }

  /**
   * Set the billing group to select.
   * @param billingGroup Billing group to use, may be <code>null</code>.
   */
  public void setBillingGroup(BillingGroup billingGroup)
  {
    _billingGroup=billingGroup;
  }

  public boolean accept(Rewards rewards)
  {
    if (_billingGroup==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof BillingTokenReward)
      {
        BillingTokenReward billingGroupReward=(BillingTokenReward)rewardElement;
        if (accept(billingGroupReward))
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

  private boolean accept(BillingTokenReward billingGroupReward)
  {
    if (_billingGroup==BillingGroup.ANY)
    {
      return true;
    }
    return (_billingGroup==billingGroupReward.getBillingGroup());
  }
}
