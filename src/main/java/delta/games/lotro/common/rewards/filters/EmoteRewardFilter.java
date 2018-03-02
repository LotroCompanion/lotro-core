package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Emote;
import delta.games.lotro.common.Rewards;

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
    Emote[] emotes=rewards.getEmotes();
    if (emotes!=null)
    {
      for(Emote emote : emotes)
      {
        if (_emote.equals(emote.getName()))
        {
          return true;
        }
      }
    }
    return false;
  }
}
