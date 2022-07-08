package delta.games.lotro.character.status.emotes;

import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Emote status.
 * @author DAM
 */
public class EmoteStatus
{
  private EmoteDescription _emote;
  private boolean _available;

  /**
   * Constructor.
   * @param emote Associated emote.
   */
  public EmoteStatus(EmoteDescription emote)
  {
    if (emote==null)
    {
      throw new IllegalArgumentException("emote is null");
    }
    _emote=emote;
    _available=false;
  }

  /**
   * Get the associated emote.
   * @return the associated emote.
   */
  public EmoteDescription getEmote()
  {
    return _emote;
  }

  /**
   * Indicates if this emote is available or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAvailable()
  {
    return _available;
  }

  /**
   * Set the 'available' flag.
   * @param available Value to set.
   */
  public void setAvailable(boolean available)
  {
    _available=available;
  }

  /**
   * Get the state of this emote.
   * @return the state of this emote.
   */
  public EmoteState getState()
  {
    boolean auto=_emote.isAuto();
    if (auto)
    {
      return EmoteState.AUTO;
    }
    return _available?EmoteState.ACQUIRED:EmoteState.NOT_KNOWN;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int emoteID=_emote.getIdentifier();
    sb.append("Emote ").append(_emote.getName()).append(" (").append(emoteID).append("): ");
    if (_available)
    {
      sb.append("available");
    }
    else
    {
      sb.append("not available");
    }
    return sb.toString();
  }
}
