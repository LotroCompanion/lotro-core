package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Emote reward.
 * @author DAM
 */
public class EmoteReward extends RewardElement
{
  private Proxy<EmoteDescription> _emote;

  /**
   * Constructor.
   * @param emoteProxy Emote proxy.
   */
  public EmoteReward(Proxy<EmoteDescription> emoteProxy)
  {
    _emote=emoteProxy;
  }

  /**
   * Get the emote proxy.
   * @return an emote proxy or <code>null</code> if not set.
   */
  public Proxy<EmoteDescription> getEmoteProxy()
  {
    return _emote;
  }

  /**
   * Get the name of the rewarded emote.
   * @return an emote name.
   */
  public String getName()
  {
    return _emote.getName();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_emote!=null)
    {
      sb.append(_emote.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
