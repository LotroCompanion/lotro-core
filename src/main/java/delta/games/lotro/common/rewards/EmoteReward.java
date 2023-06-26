package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Emote reward.
 * @author DAM
 */
public class EmoteReward extends RewardElement
{
  private EmoteDescription _emote;

  /**
   * Constructor.
   * @param emote Emote.
   */
  public EmoteReward(EmoteDescription emote)
  {
    _emote=emote;
  }

  /**
   * Get the emote.
   * @return an emote or <code>null</code> if not set.
   */
  public EmoteDescription getEmote()
  {
    return _emote;
  }

  /**
   * Get the name of the rewarded emote.
   * @return an emote name.
   */
  public String getName()
  {
    return (_emote!=null)?_emote.getName():"?";
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
