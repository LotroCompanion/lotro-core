package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.RelicReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Filter for rewards that contain a relic.
 * @author DAM
 */
public class RelicRewardFilter implements Filter<Rewards>
{
  private Integer _relicId;

  /**
   * Constructor.
   * @param relicId ID of relic to select (may be <code>null</code>).
   */
  public RelicRewardFilter(Integer relicId)
  {
    _relicId=relicId;
  }

  /**
   * Get the relic ID to use.
   * @return A relic ID or <code>null</code>.
   */
  public Integer getRelicId()
  {
    return _relicId;
  }

  /**
   * Set the relic ID to select.
   * @param relicId Relic ID to use, may be <code>null</code>.
   */
  public void setRelicId(Integer relicId)
  {
    _relicId=relicId;
  }

  public boolean accept(Rewards rewards)
  {
    if (_relicId==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof RelicReward)
      {
        RelicReward relicReward=(RelicReward)rewardElement;
        if (accept(relicReward))
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

  private boolean accept(RelicReward relicReward)
  {
    Relic relic=relicReward.getRelic();
    if (relic!=null)
    {
      return (_relicId.intValue()==relic.getIdentifier());
    }
    return false;
  }
}
