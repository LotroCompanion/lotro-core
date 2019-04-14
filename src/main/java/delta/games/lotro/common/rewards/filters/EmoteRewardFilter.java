package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;

/**
 * Filter for rewards that contain an emote.
 * @author DAM
 */
public class EmoteRewardFilter implements Filter<Rewards>
{
  private String _emote;

  /**
   * Constructor.
   * @param emote Emote to select (may be <code>null</code>).
   */
  public EmoteRewardFilter(String emote)
  {
    _emote=emote;
  }

  /**
   * Get the emote to use.
   * @return A emote or <code>null</code>.
   */
  public String getEmote()
  {
    return _emote;
  }

  /**
   * Set the emote to select.
   * @param emote Emote to use, may be <code>null</code>.
   */
  public void setEmote(String emote)
  {
    _emote=emote;
  }

  public boolean accept(Rewards rewards)
  {
    if (_emote==null)
    {
      return true;
    }
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof EmoteReward)
      {
        EmoteReward emoteReward=(EmoteReward)rewardElement;
        if (accept(emoteReward))
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

  private boolean accept(EmoteReward emoteReward)
  {
    return (_emote.equals(emoteReward.getName()));
  }
}
