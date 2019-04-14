package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TraitReward;

/**
 * Filter for rewards that contain a trait.
 * @author DAM
 */
public class TraitRewardFilter implements Filter<Rewards>
{
  private String _trait;

  /**
   * Constructor.
   * @param trait Trait to select (may be <code>null</code>).
   */
  public TraitRewardFilter(String trait)
  {
    _trait=trait;
  }

  /**
   * Get the trait to use.
   * @return A trait or <code>null</code>.
   */
  public String getTrait()
  {
    return _trait;
  }

  /**
   * Set the trait to select.
   * @param trait Trait to use, may be <code>null</code>.
   */
  public void setTrait(String trait)
  {
    _trait=trait;
  }

  public boolean accept(Rewards rewards)
  {
    if (_trait==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof TraitReward)
      {
        TraitReward traitReward=(TraitReward)rewardElement;
        if (accept(traitReward))
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

  private boolean accept(TraitReward traitReward)
  {
    return (_trait.equals(traitReward.getName()));
  }
}
