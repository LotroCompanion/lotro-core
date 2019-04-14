package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TitleReward;

/**
 * Filter for rewards that contain a title.
 * @author DAM
 */
public class TitleRewardFilter implements Filter<Rewards>
{
  private String _title;

  /**
   * Constructor.
   * @param title Title to select (may be <code>null</code>).
   */
  public TitleRewardFilter(String title)
  {
    _title=title;
  }

  /**
   * Get the title to use.
   * @return A title or <code>null</code>.
   */
  public String getTitle()
  {
    return _title;
  }

  /**
   * Set the title to select.
   * @param title Title to use, may be <code>null</code>.
   */
  public void setTitle(String title)
  {
    _title=title;
  }

  public boolean accept(Rewards rewards)
  {
    if (_title==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof TitleReward)
      {
        TitleReward titleReward=(TitleReward)rewardElement;
        if (accept(titleReward))
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

  private boolean accept(TitleReward titleReward)
  {
    return (_title.equals(titleReward.getName()));
  }
}
