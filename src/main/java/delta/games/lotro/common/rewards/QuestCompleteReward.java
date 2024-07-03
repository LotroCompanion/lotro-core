package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * 'quest complete' reward.
 * @author DAM
 */
public class QuestCompleteReward extends RewardElement
{
  /**
   * Achievable to be completed.
   */
  private Proxy<Achievable> _achievable;

  /**
   * Constructor.
   * @param achievable Achievable.
   */
  public QuestCompleteReward(Proxy<Achievable> achievable)
  {
    _achievable=achievable;
  }

  /**
   * Get the achievable.
   * @return an achievable.
   */
  public Proxy<Achievable> getAchievable()
  {
    return _achievable;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_achievable!=null)
    {
      sb.append(_achievable.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
