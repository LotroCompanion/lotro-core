package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Filter for rewards that contain a reputation gain.
 * @author DAM
 */
public class ReputationRewardFilter implements Filter<Rewards>
{
  private Faction _faction;

  /**
   * Constructor.
   * @param faction Faction to select (may be <code>null</code>).
   */
  public ReputationRewardFilter(Faction faction)
  {
    _faction=faction;
  }

  /**
   * Get the faction to use.
   * @return A faction or <code>null</code>.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Set the faction to select.
   * @param faction Faction to use, may be <code>null</code>.
   */
  public void setFaction(Faction faction)
  {
    _faction=faction;
  }

  public boolean accept(Rewards rewards)
  {
    if (_faction==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof ReputationReward)
      {
        ReputationReward reputationReward=(ReputationReward)rewardElement;
        if (accept(reputationReward))
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

  private boolean accept(ReputationReward reputationReward)
  {
    return (_faction==reputationReward.getFaction());
  }
}
